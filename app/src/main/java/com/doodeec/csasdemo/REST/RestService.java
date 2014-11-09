package com.doodeec.csasdemo.REST;

import android.net.Uri;
import android.util.Log;

import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.Model.Transaction;
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
 *
 * Rest service for connecting to Server API
 */
public class RestService {

    private enum RequestType {
        LIST, DETAIL, TRANSACTIONS
    }

    private static final String BASE_PATH = "transparentAccounts";
    private static final String LIST_PATH = "accounts";
    private static final String DETAIL_PATH = "accounts";
    private static final String TRANSACTIONS_PATH = "transactions";

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "private-anon-805bdefcb-csastransparentaccounts.apiary-mock.com";
    private static final String PAGE_KEY = "page";
    private static final String SIZE_KEY = "size";


    /**
     * Creates url according its parameters
     *
     * @param type  request type
     * @param accId account number ID
     * @param page  page to display
     * @param size  page size to load
     * @return built url
     */
    private static String buildUrl(RequestType type, String accId, Integer page, Integer size) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme(SCHEME).authority(AUTHORITY).appendPath(BASE_PATH);

        switch (type) {
            case LIST:
                builder.appendPath(LIST_PATH);
                break;
            case DETAIL:
                builder.appendPath(DETAIL_PATH).appendPath(accId);
                break;
            case TRANSACTIONS:
                builder.appendPath(TRANSACTIONS_PATH).appendPath(accId);
                if (page != null) builder.appendQueryParameter(PAGE_KEY, String.valueOf(page));
                if (size != null) builder.appendQueryParameter(SIZE_KEY, String.valueOf(size));
                break;
            default:
                break;
        }

        return builder.build().toString();
    }

    /**
     * Loads list of accounts
     *
     * @param responseListener response listener
     * @return request interface
     */
    public static ServerRequestInterface getAccounts(final ServerResponseListener<BankAccount[]> responseListener) {
        String url = buildUrl(RequestType.LIST, null, null, null);

        ServerRequest request = new ServerRequest(url, ServerRequest.RequestType.GET, new ArrayServerResponseListener() {
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

    /**
     * Loads bank account details
     *
     * @param accId            account number ID
     * @param responseListener response listener
     * @return request interface
     */
    public static ServerRequestInterface getAccountDetail(String accId, final ServerResponseListener<BankAccount> responseListener) {
        String url = buildUrl(RequestType.DETAIL, accId, null, null);

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

    /**
     * Loads account transactions
     *
     * @param accId            account number ID
     * @param page             page to display
     * @param pageSize         page size to load
     * @param responseListener response listener
     * @return request interface
     */
    public static ServerRequestInterface getAccountTransactions(String accId, final Integer page, Integer pageSize,
                                                                final ServerResponseListener<Transaction[]> responseListener) {
        String url = buildUrl(RequestType.TRANSACTIONS, accId, page, pageSize);

        ServerRequest request = new ServerRequest(url, ServerRequest.RequestType.GET, new JSONServerResponseListener() {
            @Override
            public void onSuccess(JSONObject accountDefinition) {
                Log.d("CSAS", "Account transactions loaded for page " + String.valueOf(page));

                try {
                    JSONArray listDefinition = accountDefinition.getJSONArray(Transaction.TRANSACTIONS_KEY);
                    Transaction[] transactionList = new Transaction[listDefinition.length()];

                    for (int i = 0; i < listDefinition.length(); i++) {
                        transactionList[i] = new Transaction(listDefinition.getJSONObject(i));
                    }

                    responseListener.onSuccess(transactionList);
                } catch (Exception e) {
                    Log.e("CSAS", "Transaction list has invalid structure: " + e.getMessage());
                    responseListener.onError(new ErrorResponse(e.getMessage()));
                }
            }

            @Override
            public void onError(ErrorResponse error) {
                Log.e("CSAS", "Account transactions can not be loaded: " + error.getMessage());
                responseListener.onError(error);
            }

            @Override
            public void onProgress(Integer progress) {
                responseListener.onProgress(progress);
            }

            @Override
            public void onCancelled() {
                Log.d("CSAS", "Account transactions loading cancelled");
                responseListener.onCancelled();
            }
        });
        request.execute();
        return request;
    }
}
