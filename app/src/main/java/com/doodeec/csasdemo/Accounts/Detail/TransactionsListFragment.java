package com.doodeec.csasdemo.Accounts.Detail;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.doodeec.csasdemo.R;

/**
 * Created by Dusan Doodeec Bartos on 9.11.2014.
 */
public class TransactionsListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_list_fragment, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[] {"One", "Two", "Three"}));
    }
}
