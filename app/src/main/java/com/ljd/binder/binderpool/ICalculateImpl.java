package com.ljd.binder.binderpool;

import android.os.RemoteException;

import com.ljd.binder.ICalculate;

/**
 * Created by ljd-PC on 2016/2/21.
 */
public class ICalculateImpl extends ICalculate.Stub {
    @Override
    public int add(int first, int second) throws RemoteException {
        return first + second;
    }

    @Override
    public int sub(int first, int second) throws RemoteException {
        return first - second;
    }
}
