package com.doodeec.csasdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.doodeec.csasdemo.Accounts.Detail.AccountDetailFragment;
import com.doodeec.csasdemo.Accounts.List.AccountListAdapter;
import com.doodeec.csasdemo.Accounts.List.AccountListFragment;
import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.REST.RestService;
import com.doodeec.csasdemo.ServerRequest.ErrorResponse;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;


public class MainActivity extends ActionBarActivity {

    private AccountListFragment mListFragment;
    private AccountListAdapter mAdapter;

    private static final String DETAIL_FRG_TAG = "Acc_Detail";
    private final AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            openAccountDetail(mAdapter.getItem(position));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListFragment = (AccountListFragment) getSupportFragmentManager().findFragmentById(R.id.account_list);

        if (mListFragment == null) {
            throw new AssertionError("Activity has invalid layout");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        RestService.getAccounts(new ServerResponseListener<BankAccount[]>() {
            @Override
            public void onSuccess(BankAccount[] accountList) {
                mAdapter = new AccountListAdapter(MainActivity.this, accountList);
                mListFragment.getListView().setAdapter(mAdapter);
                mListFragment.getListView().setOnItemClickListener(mItemClickListener);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openAccountDetail(BankAccount account) {
        AccountDetailFragment detailFragment = new AccountDetailFragment();
        detailFragment.setAccount(account.getId());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(android.R.id.content, detailFragment, DETAIL_FRG_TAG);
        transaction.addToBackStack(DETAIL_FRG_TAG);
        transaction.commit();
    }
}
