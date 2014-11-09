package com.doodeec.csasdemo.Accounts.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.Model.Transaction;
import com.doodeec.csasdemo.R;
import com.doodeec.csasdemo.REST.RestService;
import com.doodeec.csasdemo.ServerRequest.ErrorResponse;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;

/**
 * Created by Dusan Doodeec Bartos on 8.11.2014.
 */
public class AccountDetailFragment extends Fragment {

    private String mAccountId;
    private BankAccount mAccount;
    private TextView mAccIdText;
    private TextView mAccNameText;
    private TextView mAccDescText;
    private ImageView mExpandBtn;
    private final View.OnClickListener mExpandListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandTransactions();
        }
    };

    public void setAccount(String accountId) {
        mAccountId = accountId;
    }

    @SuppressWarnings("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_detail_fragment, null);
        mAccIdText = (TextView) v.findViewById(R.id.acc_id);
        mAccNameText = (TextView) v.findViewById(R.id.acc_name);
        mAccDescText = (TextView) v.findViewById(R.id.acc_description);
        mExpandBtn = (ImageView) v.findViewById(R.id.expand_balance);

        if (mAccIdText == null || mAccNameText == null || mAccDescText == null || mExpandBtn == null) {
            throw new AssertionError("Account detail has invalid layout");
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestService.getAccountDetail(mAccountId, new ServerResponseListener<BankAccount>() {
            @Override
            public void onSuccess(BankAccount account) {
                mAccount = account;
                setData();
                mExpandBtn.setOnClickListener(mExpandListener);
            }

            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onProgress(Integer progress) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    public void setData() {
        mAccIdText.setText(mAccount.getId());
        mAccNameText.setText(mAccount.getName());
        mAccDescText.setText(mAccount.getDescription());
    }

    private void expandTransactions() {
        RestService.getAccountTransactions(mAccountId, null, null, new ServerResponseListener<Transaction[]>() {
            @Override
            public void onSuccess(Transaction[] transactions) {

            }

            @Override
            public void onError(ErrorResponse error) {

            }

            @Override
            public void onProgress(Integer progress) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }
}
