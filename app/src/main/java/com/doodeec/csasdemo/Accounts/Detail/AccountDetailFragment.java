package com.doodeec.csasdemo.Accounts.Detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.doodeec.csasdemo.Helper;
import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.R;
import com.doodeec.csasdemo.TransactionList.TransactionListFragment;

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
    private TextView mIbanText;
    private TextView mBalanceText;
    private TextView mCurrencyText;
    private ImageView mArrowView;
    private SlidingDrawer mSlidingDrawer;

    private final SlidingDrawer.OnDrawerOpenListener mOpenListener = new SlidingDrawer.OnDrawerOpenListener() {
        @Override
        public void onDrawerOpened() {
            mArrowView.setImageDrawable(Helper.getDrawable(R.drawable.ic_action_expand));
        }
    };
    private final SlidingDrawer.OnDrawerCloseListener mCloseListener = new SlidingDrawer.OnDrawerCloseListener() {
        @Override
        public void onDrawerClosed() {
            mArrowView.setImageDrawable(Helper.getDrawable(R.drawable.ic_action_collapse));
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
        mIbanText = (TextView) v.findViewById(R.id.acc_iban);

        mBalanceText = (TextView) v.findViewById(R.id.acc_balance);
        mCurrencyText = (TextView) v.findViewById(R.id.acc_currency);
        mArrowView = (ImageView) v.findViewById(R.id.expand_balance);

        mSlidingDrawer = (SlidingDrawer) v.findViewById(R.id.sliding_drawer);

        if (mAccIdText == null || mAccNameText == null || mAccDescText == null || mIbanText == null ||
                mBalanceText == null || mCurrencyText == null) {
            throw new AssertionError("Account detail has invalid layout");
        }

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TransactionListFragment transactionListFragment = new TransactionListFragment();
        transactionListFragment.setAccountId(mAccount.getId());

        getFragmentManager()
                .beginTransaction()
                .add(R.id.transactions, transactionListFragment, "TRANSACTIONS")
                .commit();

        setData();

        mSlidingDrawer.setOnDrawerOpenListener(mOpenListener);
        mSlidingDrawer.setOnDrawerCloseListener(mCloseListener);
    }

    /**
     * Sets data to the view
     */
    public void setData() {
        mAccIdText.setText(mAccount.getId());
        mAccNameText.setText(mAccount.getName());
        mAccDescText.setText(mAccount.getDescription());
        mIbanText.setText(mAccount.getIban());

        mBalanceText.setText(String.format("%,.2f", mAccount.getBalance()));
        mCurrencyText.setText(mAccount.getCurrency());
    }
}
