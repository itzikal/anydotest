package com.alkotzer.itzik.anydotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ConnectionManager mConnectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectionManager = new WebSockectConnectionManager();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mConnectionManager.connect(new OnItemReceivedListener() {
            @Override
            public void onItemReceived(final BasketItem item)
            {
                Log.d(LOG_TAG, "onItemReceived(), item: " + item);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mConnectionManager.disconnect();
    }
}
