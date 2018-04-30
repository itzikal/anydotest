package com.alkotzer.itzik.anydotest.interfaces;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

public interface ConnectionManager
{
    void connect(OnItemReceivedListener listener);
    void disconnect();
    void pause();
    void resume();
    boolean isPaused();
}
