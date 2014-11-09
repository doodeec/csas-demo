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
 *
 * Acount Detail fragment, displays details about bank account
 */
public class AccountDetailFragment extends Fragment {

    private BankAccount mAccount;
    private TextView mAccIdText;
    private TextView mAccNameText;
    private TextView mAccDescText;
    private TextView mBalanceText;
    private TextView mCurrencyText;
    private ImageView mExpandBtn;
    private final View.OnClickListener mExpandListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            expandTransactions();
        }
    };

    /**
     * Sets id of account to display detail of
     *
     * @param account account to display
     */
    public void setAccount(BankAccount account) {
        mAccount = account;
    }

    @SuppressWarnings("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_detail_fragment, null);
        mAccIdText = (TextView) v.findViewById(R.id.acc_id);
        mAccNameText = (TextView) v.findViewById(R.id.acc_name);
        mAccDescText = (TextView) v.findViewById(R.id.acc_description);
        mBalanceText = (TextView) v.findViewById(R.id.acc_balance);
        mCurrencyText = (TextView) v.findViewById(R.id.acc_currency);
        mExpandBtn = (ImageView) v.findViewById(R.id.expand_balance);

        if (mAccIdText == null || mAccNameText == null || mAccDescText == null || mExpandBtn == null ||
                mBalanceText == null || mCurrencyText == null) {
            throw new AssertionError("Account detail has invalid layout");
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
        mExpandBtn.setOnClickListener(mExpandListener);
    }

    /**
     * Sets data to the view
     */
    public void setData() {
        mAccIdText.setText(mAccount.getId());
        mAccNameText.setText(mAccount.getName());
        mAccDescText.setText(mAccount.getDescription());
        mBalanceText.setText(String.format("%,.2f", mAccount.getBalance()));
        mCurrencyText.setText(mAccount.getCurrency());
    }

    /**
     * Loads account transactions
     */
    private void expandTransactions() {
        RestService.getAccountTransactions(mAccount.getId(), null, null, new ServerResponseListener<Transaction[]>() {
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
