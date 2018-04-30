package com.alkotzer.itzik.anydotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final int color)
            {
                if(mConnectionManager.isPaused())
                {
                    Intent intent = JustBackgroundColorActivity.crearteIntent(MainActivity.this, color);
                    startActivity(intent);
                }
            }
        });
        final RecyclerView list = (RecyclerView) findViewById(R.id.basket_list);
        list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        list.setAdapter(mAdapter);

        final Button enableConnection = (Button) findViewById(R.id.pause_connection);
        enableConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                if(mConnectionManager.isPaused())
                {
                    mConnectionManager.resume();
                    enableConnection.setText(R.string.pause_connection);
                }
                else
                {
                    mConnectionManager.pause();
                    enableConnection.setText(R.string.resume_connection);
                }
            }
        });
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
