package com.doodeec.csasdemo.Accounts.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doodeec.csasdemo.R;

/**
 * Created by Dusan Doodeec Bartos on 8.11.2014.
 */
public class AccountListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_list_fragment, container);
    }
}
