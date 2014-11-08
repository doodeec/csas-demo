package com.doodeec.csasdemo.ServerRequest.ResponseListener;

import com.doodeec.csasdemo.ServerRequest.ErrorResponse;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Generic Server response listener
 */
public interface ServerResponseListener<T> {

    /**
     * Fires when request was successfully evaluated
     *
     * @param responseObject response object parsed from server response
     */
    public void onSuccess(T responseObject);

    /**
     * Fires when response is evaluated as unsuccessful
     *
     * @param error error message
     * @see com.doodeec.csasdemo.ServerRequest.ErrorResponse
     */
    public void onError(ErrorResponse error);

    /**
     * Fires when {@link android.os.AsyncTask#onProgressUpdate(Object[])} is fired
     *
     * @param progress progress value in percent
     */
    public void onProgress(Integer progress);

    /**
     * Fires when request was cancelled before its fulfillment
     */
    public void onCancelled();
}
