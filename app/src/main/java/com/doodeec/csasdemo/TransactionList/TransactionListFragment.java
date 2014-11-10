package com.doodeec.csasdemo.TransactionList;

import com.doodeec.csasdemo.LazyList.LazyList;
import com.doodeec.csasdemo.Model.Transaction;
import com.doodeec.csasdemo.REST.RestService;
import com.doodeec.csasdemo.ServerRequest.ErrorResponse;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;

import java.util.Arrays;

/**
 * Created by dbartos on 31.10.2014.
 *
 * Implementation of LazyList Class
 */
public class TransactionListFragment extends LazyList<Transaction> {

    private String mAccountId;

    @Override
    protected void initAdapter() {
        mAdapter = new TransactionListAdapter(mData, this);
        loadPage(++mPage);
    }

    public void setAccountId(String id) {
        mAccountId = id;
    }

    /**
     * Loads data for specific page
     *
     * @param page page to load
     */
    @Override
    protected synchronized void loadPage(final int page) {
        super.loadPage(page);

        RestService.getAccountTransactions(mAccountId, page, null, new ServerResponseListener<Transaction[]>() {
            @Override
            public void onSuccess(Transaction[] transactions) {
                if (transactions.length > 0) {
                    mData.addAll(Arrays.asList(transactions));
                    onDataLoadingCompleted(page);
                } else {
                    onDataLoadingFailed(REASON_LIST_END, null);
                }
            }

            @Override
            public void onError(ErrorResponse error) {
                onDataLoadingFailed(LazyList.REASON_SERVER_ERROR, page);
            }

            @Override
            public void onCancelled() {
                onDataLoadingFailed(LazyList.REASON_REQUEST_CANCELLED, page);
            }
        });
    }
}
