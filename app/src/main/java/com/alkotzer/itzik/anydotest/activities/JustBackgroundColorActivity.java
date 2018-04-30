package com.alkotzer.itzik.anydotest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alkotzer.itzik.anydotest.R;

public class JustBackgroundColorActivity extends AppCompatActivity
{
    private static final String BACKGROUND_COLOR_EXTRA = "whats_my_background";
    public static Intent crearteIntent(Context context, int color)
    {
        Intent intent = new Intent(context, JustBackgroundColorActivity.class);
        intent.putExtra(BACKGROUND_COLOR_EXTRA, color);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_background_color);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        int color = extras.getInt(BACKGROUND_COLOR_EXTRA, 0);

        findViewById(R.id.container).setBackgroundColor(color);



    }
}
