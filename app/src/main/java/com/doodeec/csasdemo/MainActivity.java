package com.doodeec.csasdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.doodeec.csasdemo.Model.BankAccount;
import com.doodeec.csasdemo.REST.RestService;
import com.doodeec.csasdemo.ServerRequest.ErrorResponse;
import com.doodeec.csasdemo.ServerRequest.ResponseListener.ServerResponseListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestService.getAccounts(new ServerResponseListener<BankAccount[]>() {
            @Override
            public void onSuccess(BankAccount[] responseObject) {

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
}
