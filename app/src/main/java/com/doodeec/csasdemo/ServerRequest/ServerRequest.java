package com.doodeec.csasdemo.ServerRequest;

import android.os.AsyncTask;
import android.util.Log;

import com.doodeec.csasdemo.Helper;
import com.doodeec.csasdemo.R;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ArrayServerResponseListener;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.JSONServerResponseListener;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Dusan Doodeec Bartos on 20.10.2014.
 *
 * Asynchronous Server request
 */
public class ServerRequest extends AsyncTask<Void, Integer, Void> implements ServerRequestInterface {

    public enum RequestType {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        public String methodName;

        private RequestType(String methodName) {
            this.methodName = methodName;
        }
    }

    private enum ResponseType {
        ARRAY, JSON
    }

    // asyncTask progress
    private static final int PROGRESS_IDLE = 0;
    private static final int PROGRESS_OPENED = 10;
    private static final int PROGRESS_CONNECTED = 20;
    private static final int PROGRESS_RESPONSE_CODE = 40;
    private static final int PROGRESS_RESPONSE_TYPE = 50;
    private static final int PROGRESS_CONTENT = 70;
    private static final int PROGRESS_CONNECTION_CLOSE = 80;
    private static final int PROGRESS_DISCONNECTING = 90;
    private static final int PROGRESS_DONE = 100;

    // response headers
    private static final String REQ_CHARSET_KEY = "Accept-Charset";
    private static final String REQ_CHARSET_VALUE = "UTF-8";
    private static final String REQ_ENCODING_KEY = "Accept-Encoding";
    private static final String REQ_ENCODING_VALUE = "gzip";
    private static final String REQ_CONTENT_TYPE_KEY = "Content-Type";
    private static final String REQ_CONTENT_TYPE_VALUE = "application/json; charset=utf8";

    // response types
    private static final String[] JSON_RESPONSE = new String[]{"application/json"};

    private String mUrl;
    private RequestType mType;
    private ResponseType mResponseType;
    private ServerResponseListener<JSONObject> mJSONResponseListener;
    private ServerResponseListener<JSONArray> mArrayResponseListener;

    // responses
    private JSONObject mJsonObject;
    private JSONArray mArray;

    private String mErrorMessage;

    /**
     * Constructor for Server request with Array response listener
     *
     * @param url                server url
     * @param type               request type
     * @param onResponseListener array response listener
     */
    public ServerRequest(String url, RequestType type, ArrayServerResponseListener onResponseListener) {
        this.mType = type;
        this.mUrl = url;
        this.mResponseType = ResponseType.ARRAY;
        this.mArrayResponseListener = onResponseListener;
    }

    /**
     * Constructor for Server request with JSON response listener
     *
     * @param url                server url
     * @param type               request type
     * @param onResponseListener json response listener
     */
    public ServerRequest(String url, RequestType type, JSONServerResponseListener onResponseListener) {
        this.mType = type;
        this.mUrl = url;
        this.mResponseType = ResponseType.JSON;
        this.mJSONResponseListener = onResponseListener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection connection;
        publishProgress(PROGRESS_IDLE);

        Log.d("SERVER REQUEST", mUrl);
        // try to parse url string to URL object and open HTTP connection
        try {
            URL url = new URL(mUrl);
            connection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            this.mErrorMessage = e.getLocalizedMessage();
            return null;
        }

        publishProgress(PROGRESS_OPENED);

//        connection.setUseCaches(false);

        // set connection header properties
        connection.setRequestProperty(REQ_CHARSET_KEY, REQ_CHARSET_VALUE);
        connection.setRequestProperty(REQ_ENCODING_KEY, REQ_ENCODING_VALUE);
        connection.setRequestProperty(REQ_CONTENT_TYPE_KEY, REQ_CONTENT_TYPE_VALUE);
        connection.setAllowUserInteraction(true);

        // set connection type
        // since RequestType is enum, exception should never occur
        try {
            connection.setRequestMethod(mType.methodName);
        } catch (ProtocolException e) {
            this.mErrorMessage = e.getLocalizedMessage();

            throw new IllegalArgumentException("Request type set to wrong value");
        }

        try {
            connection.connect();
            publishProgress(PROGRESS_CONNECTED);

            // Checking for cancelled flag in major thread breakpoints
            if (isCancelled()) {
                connection.disconnect();
                return null;
            }

            int status = connection.getResponseCode();
            publishProgress(PROGRESS_RESPONSE_CODE);

            // Checking for cancelled flag in major thread breakpoints
            if (isCancelled()) {
                connection.disconnect();
                return null;
            }

            if (status != HttpURLConnection.HTTP_OK) {
                this.mErrorMessage = String.format(Helper.getString(R.string.server_error), status, connection.getResponseMessage());
                return null;
            }

            // Checking for cancelled flag in major thread breakpoints
            if (isCancelled()) {
                connection.disconnect();
                return null;
            }

            String contentType = connection.getContentType();
            publishProgress(PROGRESS_RESPONSE_TYPE);

            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                publishProgress(PROGRESS_CONTENT);

                if (isJSON(contentType)) {
                    switch (mResponseType) {
                        case ARRAY:
                            this.mArray = new JSONArray(parseReceivedData(inputStream));
                            break;
                        case JSON:
                            this.mJsonObject = new JSONObject(parseReceivedData(inputStream));
                            break;
                        default:
                            break;
                    }
                } else {
                    this.mErrorMessage = Helper.getString(R.string.unexpected_response);
                }
            } finally {
                publishProgress(PROGRESS_CONNECTION_CLOSE);
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            if (e.getLocalizedMessage() != null) {
                this.mErrorMessage = e.getLocalizedMessage();
            } else if (e instanceof SocketTimeoutException) {
                this.mErrorMessage = Helper.getString(R.string.connection_timeout);
            }

            return null;
        } finally {
            publishProgress(PROGRESS_DISCONNECTING);
            connection.disconnect();
            publishProgress(PROGRESS_DONE);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate();
        switch (mResponseType) {
            case ARRAY:
                mArrayResponseListener.onProgress(progress[0]);
                break;
            case JSON:
                mJSONResponseListener.onProgress(progress[0]);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        switch (mResponseType) {
            case ARRAY:
                if (isCancelled()) {
                    // no need to call listener.onCancelled, it is called via asyncTask.onCancelled()
                    return;
                } else if (mArray != null) {
                    mArrayResponseListener.onSuccess(mArray);
                } else {
                    mArrayResponseListener.onError(new ErrorResponse(mErrorMessage));
                }
                break;
            case JSON:
                if (isCancelled()) {
                    // no need to call listener.onCancelled, it is called via asyncTask.onCancelled()
                    return;
                } else if (mJsonObject != null) {
                    mJSONResponseListener.onSuccess(mJsonObject);
                } else {
                    mJSONResponseListener.onError(new ErrorResponse(mErrorMessage));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCancelled() {
        switch (mResponseType) {
            case ARRAY:
                mArrayResponseListener.onCancelled();
                break;
            case JSON:
                mJSONResponseListener.onCancelled();
                break;
            default:
                break;
        }
    }

    /**
     * Determines if response type belongs to JSON
     *
     * @param contentType content type to check
     * @return true if content type equals JSON type
     */
    private boolean isJSON(String contentType) {
        for (String mime : JSON_RESPONSE) {
            if (contentType.contains(mime)) return true;
        }
        return false;
    }

    private String parseReceivedData(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }
}
