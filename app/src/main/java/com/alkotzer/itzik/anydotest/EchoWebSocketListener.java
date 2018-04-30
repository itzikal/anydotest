package com.alkotzer.itzik.anydotest;

import android.util.Log;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

public class EchoWebSocketListener extends WebSocketListener
{
    private static final String LOG_TAG = EchoWebSocketListener.class.getSimpleName();
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(LOG_TAG, "onOpen(), "  + response);
        webSocket.request();
   //       webSocket.send("Hello, it's SSaurel !");
      //  webSocket.send("What's up ?");
        //webSocket.send(ByteString.decodeHex("deadbeef"));
        //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(LOG_TAG, "Receiving : " + text);

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(LOG_TAG, "Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.d(LOG_TAG, "Closing : " + code + " / " + reason);
    }
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(LOG_TAG,"Error : " + t.getMessage());
    }


}