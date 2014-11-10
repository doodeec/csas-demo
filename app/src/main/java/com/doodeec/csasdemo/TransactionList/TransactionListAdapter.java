package com.doodeec.csasdemo.TransactionList;

import android.view.View;
import android.view.ViewGroup;

import com.doodeec.csasdemo.LazyList.LazyListAdapter;
import com.doodeec.csasdemo.Model.Transaction;
import com.doodeec.csasdemo.R;

import java.util.List;

/**
 * Created by Dusan Doodeec Bartos on 1.11.2014.
 *
 * Implementation of lazy list adapter for transactions
 *
 * @see com.doodeec.csasdemo.LazyList.LazyListAdapter
 */
public class TransactionListAdapter extends LazyListAdapter<Transaction> {

    public TransactionListAdapter(List<Transaction> transactions, TransactionListFragment list) {
        super(transactions, list);
    }

    @SuppressWarnings("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TransactionListItemHolder holder;

        // init holder pattern
        if (convertView == null) {
            v = mInflater.inflate(R.layout.transaction_list_item, null);
            holder = new TransactionListItemHolder(v);
            v.setTag(holder);
        }

        holder = (TransactionListItemHolder) v.getTag();

        Transaction transaction = getItem(position);

        // set basic transaction information
        holder.setAmount(String.format("%,.2f", transaction.getAmount()));
        holder.setCurrency(transaction.getCurrency());
        holder.setValuationDate(transaction.getValuationDate());
        if (transaction.getSenderName() != null) {
            holder.setSender(transaction.getSenderName());
        } else if(transaction.getSenderDescription() != null) {
            holder.setSender(transaction.getSenderDescription());
        } else {
            holder.setSender(transaction.getSender());
        }

        return v;
    }
}
