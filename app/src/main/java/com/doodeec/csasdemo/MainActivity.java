package com.doodeec.csasdemo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
            // triggers opening account detail
            openAccountDetail(mAdapter.getItem(position).getId());
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
        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sync) {
            loadList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Loads account list and initializes listView adapter
     */
    private void loadList() {
        RestService.getAccounts(new ServerResponseListener<BankAccount[]>() {
            @Override
            public void onSuccess(BankAccount[] accountList) {
                mAdapter = new AccountListAdapter(MainActivity.this, accountList);
                mListFragment.getListView().setAdapter(mAdapter);
                mListFragment.getListView().setOnItemClickListener(mItemClickListener);
            }

            @Override
            public void onError(ErrorResponse error) {
                Log.e("CSAS", "Accounts list loading error: " + error.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error").setMessage("Accounts list could not be loaded").show();
            }

            @Override
            public void onProgress(Integer progress) {}

            @Override
            public void onCancelled() {}
        });
    }

    /**
     * Opens account detail in a new fragment
     *
     * @param accountId ID of account to display
     */
    private void openAccountDetail(String accountId) {
        RestService.getAccountDetail(accountId, new ServerResponseListener<BankAccount>() {
            @Override
            public void onSuccess(BankAccount account) {
                // display detail fragment
                AccountDetailFragment detailFragment = new AccountDetailFragment();
                detailFragment.setAccount(account);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_right, R.anim.slide_from_right, R.anim.slide_to_right);
                transaction.add(android.R.id.content, detailFragment, DETAIL_FRG_TAG);
                transaction.addToBackStack(DETAIL_FRG_TAG);
                transaction.commit();
            }

            @Override
            public void onError(ErrorResponse error) {
                Log.e("CSAS", "Account detail loading error: " + error.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error").setMessage("Account dialog could not be loaded").show();
            }

            @Override
            public void onProgress(Integer progress) {}

            @Override
            public void onCancelled() {}
        });
    }
}
