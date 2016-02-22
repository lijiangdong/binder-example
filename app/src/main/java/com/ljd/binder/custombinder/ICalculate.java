package com.ljd.binder.custombinder;

import android.os.IInterface;

/**
 * Created by ljd-PC on 2016/2/21.
 */
public interface ICalculate extends IInterface {

    static final int TRANSACTION_add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_sub = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public int add(int first, int second) throws android.os.RemoteException;

    public int sub(int first, int second) throws android.os.RemoteException;
}
