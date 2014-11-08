package com.doodeec.csasdemo.ServerRequest;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Interface for handling (cancelling) server requests (i.e. image loading)
 * Simplified AsyncTask - since calling context does not need an access to all AsyncTask methods
 */
public interface ServerRequestInterface {

    /**
     * @see android.os.AsyncTask#cancel(boolean)
     */
    public boolean cancel(boolean mayInterruptIfRunning);
}