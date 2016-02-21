package com.ljd.binder.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ljd.binder.ICalculate;
import com.ljd.binder.IRect;
import com.ljd.binder.R;

public class BinderPoolActivity extends AppCompatActivity {

    private final String TAG = "BinderPoolActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork(){
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder calculateBinder = binderPool.queryBinder(BinderPool.BINDER_CALCULATE);
        ICalculate iCalculate = ICalculateImpl.asInterface(calculateBinder);
        try {
            Log.e(TAG,String.valueOf(iCalculate.add(1,1)));
            Log.e(TAG,String.valueOf(iCalculate.sub(1,1)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        IBinder rect = binderPool.queryBinder(BinderPool.BINDER_RECT);
        IRect iRect = IRectImpl.asInterface(rect);
        try {
            Log.e(TAG,String.valueOf(iRect.area(1,1)));
            Log.e(TAG,String.valueOf(iRect.perimeter(1,1)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
