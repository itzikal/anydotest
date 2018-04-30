package com.alkotzer.itzik.anydotest;

/**
 * Created by itzikalkotzer on 30/04/2018.
 */

interface ConnectionManager
{
    void connect(OnItemReceivedListener listener);
    void disconnect();
    void pause();
}
