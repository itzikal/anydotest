package com.alkotzer.itzik.anydotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ConnectionManager mConnectionManager;
    private BasketItemsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectionManager = new WebSockectConnectionManager();

        mAdapter = new BasketItemsListAdapter(MainActivity.this);
        final RecyclerView list = (RecyclerView) findViewById(R.id.basket_list);
        list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        list.setAdapter(mAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mConnectionManager.connect(new OnItemReceivedListener() {
            @Override
            public void onItemReceived(final BasketItem item)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        Log.d(LOG_TAG, "onItemReceived(), item: " + item);
                        mAdapter.addItem(item);
                    }
                });

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
