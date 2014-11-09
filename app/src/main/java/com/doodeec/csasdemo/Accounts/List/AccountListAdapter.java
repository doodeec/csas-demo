package com.doodeec.csasdemo.Accounts.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.R;

/**
 * Created by Dusan Doodeec Bartos on 8.11.2014.
 */
public class AccountListAdapter extends ArrayAdapter<BankAccount> {

    private BankAccount[] mAccounts;
    private LayoutInflater mInflater;

    public AccountListAdapter(Context context, BankAccount[] accounts) {
        super(context, R.layout.account_item, accounts);

        mInflater = LayoutInflater.from(context);
        mAccounts = accounts;
    }

    @SuppressWarnings("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BankAccount account = mAccounts[position];
        AccountItemHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.account_item, null);
            holder = new AccountItemHolder(convertView);
            convertView.setTag(holder);
        }

        holder = (AccountItemHolder) convertView.getTag();
        holder.setId(account.getId());
        holder.setName(account.getName());

        return convertView;
    }

    private class AccountItemHolder {
        protected TextView mIdTextView;
        protected TextView mNameTextView;

        protected AccountItemHolder(View view) {
            mIdTextView = (TextView) view.findViewById(R.id.account_id);
            mNameTextView = (TextView) view.findViewById(R.id.account_name);

            if (mIdTextView == null || mNameTextView == null) {
                throw new AssertionError("Account item holder has invalid layout");
            }
        }

        protected void setId(String id) {
            mIdTextView.setText(id);
        }

        protected void setName(String name) {
            mNameTextView.setText(name);
        }
    }
}
