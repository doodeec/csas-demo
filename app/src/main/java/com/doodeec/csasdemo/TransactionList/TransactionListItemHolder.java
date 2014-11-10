package com.doodeec.csasdemo.TransactionList;

import android.view.View;
import android.widget.TextView;

import com.doodeec.csasdemo.R;

/**
 * Created by Dusan Doodeec Bartos on 27.10.2014.
 *
 * Transaction item - holder pattern
 */
public class TransactionListItemHolder {

    private TextView mAmountView;
    private TextView mCurrencyView;
    private TextView mSenderView;
    private TextView mDateView;

    public TransactionListItemHolder(View view) {
        mAmountView = (TextView) view.findViewById(R.id.amount);
        mCurrencyView = (TextView) view.findViewById(R.id.currency);
        mSenderView = (TextView) view.findViewById(R.id.sender);
        mDateView = (TextView) view.findViewById(R.id.date);

        if (mAmountView == null || mCurrencyView == null || mSenderView == null) {
            throw new AssertionError("Transaction holder does not have required structure");
        }
    }

    public void setAmount(String amount) {
        mAmountView.setText(amount);
    }

    public void setCurrency(String currency) {
        mCurrencyView.setText(currency);
    }

    public void setSender(String sender) {
        mSenderView.setText(sender);
    }

    public void setValuationDate(String dateString) {
        mDateView.setText(dateString);
    }

}
