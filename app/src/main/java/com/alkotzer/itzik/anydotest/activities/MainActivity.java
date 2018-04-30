package com.alkotzer.itzik.anydotest.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.alkotzer.itzik.anydotest.R;
import com.alkotzer.itzik.anydotest.adapters.BasketItemsListAdapter;
import com.alkotzer.itzik.anydotest.interfaces.ConnectionManager;
import com.alkotzer.itzik.anydotest.interfaces.OnItemClickListener;
import com.alkotzer.itzik.anydotest.interfaces.OnItemReceivedListener;
import com.alkotzer.itzik.anydotest.model.BasketItem;
import com.alkotzer.itzik.anydotest.network.WebSockectConnectionManager;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ConnectionManager mConnectionManager;
    private BasketItemsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_main);

        mConnectionManager = new WebSockectConnectionManager();

        mAdapter = new BasketItemsListAdapter(MainActivity.this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int color)
            {
                if(mConnectionManager.isPaused())
                {
                    Intent intent = JustBackgroundColorActivity.crearteIntent(MainActivity.this, color);
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(MainActivity.this, view, "robot");
                    startActivity(intent, options.toBundle());
                }
            }
        });
        final RecyclerView list = findViewById(R.id.basket_list);
        list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        list.setAdapter(mAdapter);

        final Button enableConnection = findViewById(R.id.pause_connection);
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

    private void setupWindowAnimations()
    {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
    }
}
