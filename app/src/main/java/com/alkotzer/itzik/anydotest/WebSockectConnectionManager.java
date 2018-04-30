package com.alkotzer.itzik.anydotest;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

public class WebSockectConnectionManager implements ConnectionManager
{
    private WebSocket mWebSocket;
    private final Gson mGson = new Gson();
    private final WebSocketListener mWebSocketListener = new WebSocketListener()
    {
        private final String LOG_TAG = WebSockectConnectionManager.class.getSimpleName();
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response)
        {
            Log.d(LOG_TAG, "onOpen(), " + response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text)
        {
            Log.d(LOG_TAG, "Receiving : " + text);
            if(mListener != null && !mIsPaused)
            {
                mListener.onItemReceived(mGson.fromJson(text, BasketItem.class));
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes)
        {
            Log.d(LOG_TAG, "Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason)
        {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.d(LOG_TAG, "Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response)
        {
            Log.d(LOG_TAG, "Error : " + t.getMessage());
        }
    };
    private OnItemReceivedListener mListener;
    private boolean mIsPaused = false;

    @Override
    public void connect(OnItemReceivedListener listener) {
        mListener = listener;
        //mWebSocket://" + server + "/receive"
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://superdo-groceries.herokuapp.com/receive").build();

        mWebSocket = client.newWebSocket(request, mWebSocketListener);

        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void disconnect()
    {
        mWebSocket.cancel();
    }

    @Override
    public void pause()
    {
        mIsPaused = true;
    }

    public void resume(){
        mIsPaused = false;
    }
}