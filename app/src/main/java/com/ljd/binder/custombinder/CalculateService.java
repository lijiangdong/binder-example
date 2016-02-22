package com.ljd.binder.custombinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class CalculateService extends Service {
    private Calculate mCalculate;
    public CalculateService() {
        mCalculate = new Calculate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mCalculate;
    }
}
