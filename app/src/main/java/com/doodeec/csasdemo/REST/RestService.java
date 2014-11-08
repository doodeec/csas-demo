package com.doodeec.csasdemo.REST;

import android.net.Uri;
import android.util.Log;

import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.ServerRequest.ErrorResponse;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ArrayServerResponseListener;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.JSONServerResponseListener;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;
import com.doodeec.csasdemo.ServerRequest.ServerRequest;
import com.doodeec.csasdemo.ServerRequest.ServerRequestInterface;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Dusan Doodeec Bartos on 8.11.2014.
 */
public class RestService {

    private static final String BASE_URL = "http://private-anon-805bdefcb-csastransparentaccounts.apiary-mock.com/";
    private static final String LIST_URL = BASE_URL + "transparentAccounts/accounts";
    private static final String DETAIL_URL = BASE_URL + "transparentAccounts/accounts/%s";
    private static final String TRANSACTIONS_URL = BASE_URL + "transparentAccounts/accounts";

    private static final String SCHEME = "http";
    private static final String PATH_KEY = "movies";
    private static final String PAGE_KEY = "page";
    //    private static final int PAGE_LIMIT = 5;
    private static final String MOCK_AUTHORITY = "api.doodeec.com";

    /**
     * Builds API url for specific page of movie list
     *
     * @param page page to load
     * @return API url
     */
    private static String buildPathForPage(int page) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme(SCHEME)
                .authority(MOCK_AUTHORITY)
                .appendPath(PATH_KEY)
                .appendQueryParameter(PAGE_KEY, String.valueOf(page));

        return builder.build().toString();
    }

    /**
     * Loads account list
     *
     * @param responseListener response listener
     * @return request interface
     */
    public static ServerRequestInterface getAccounts(final ServerResponseListener<BankAccount[]> responseListener) {
        ServerRequest request = new ServerRequest(LIST_URL, ServerRequest.RequestType.GET, new ArrayServerResponseListener() {
            @Override
            public void onSuccess(JSONArray accountListDefinition) {
                Log.d("CSAS", "Accounts loaded");

                try {
                    BankAccount[] accountList = new BankAccount[accountListDefinition.length()];

                    for (int i = 0; i < accountListDefinition.length(); i++) {
                        accountList[i] = new BankAccount(accountListDefinition.getJSONObject(i));
                    }

                    responseListener.onSuccess(accountList);
                } catch (Exception e) {
                    Log.e("CSAS", "Account list has invalid structure: " + e.getMessage());
                    responseListener.onError(new ErrorResponse(e.getMessage()));
                }
            }

            @Override
            public void onError(ErrorResponse error) {
                Log.e("CSAS", "Accounts can not be loaded: " + error.getMessage());
                responseListener.onError(error);
            }

            @Override
            public void onProgress(Integer progress) {
                responseListener.onProgress(progress);
            }

            @Override
            public void onCancelled() {
                Log.d("CSAS", "Accounts loading cancelled");
                responseListener.onCancelled();
            }
        });
        request.execute();
        return request;
    }

    public static ServerRequestInterface getAccountDetail(String accId, final ServerResponseListener<BankAccount> responseListener) {
        String url = String.format(DETAIL_URL, accId);

        ServerRequest request = new ServerRequest(url, ServerRequest.RequestType.GET, new JSONServerResponseListener() {
            @Override
            public void onSuccess(JSONObject accountDefinition) {
                Log.d("CSAS", "Account detail loaded");
                responseListener.onSuccess(new BankAccount(accountDefinition));
            }

            @Override
            public void onError(ErrorResponse error) {
                Log.e("CSAS", "Account detail can not be loaded: " + error.getMessage());
                responseListener.onError(error);
            }

            @Override
            public void onProgress(Integer progress) {
                responseListener.onProgress(progress);
            }

            @Override
            public void onCancelled() {
                Log.d("CSAS", "Account detail loading cancelled");
                responseListener.onCancelled();
            }
        });
        request.execute();
        return request;
    }
}
