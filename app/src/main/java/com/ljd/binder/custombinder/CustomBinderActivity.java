package com.ljd.binder.custombinder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ljd.binder.*;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomBinderActivity extends AppCompatActivity {


    private final String TAG = "CustomBinderActivity";
    private ICalculate mCalculate;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mCalculate != null){
                mCalculate.asBinder().unlinkToDeath(mDeathRecipient,0);
                mCalculate = null;
                bindService();
            }
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"Bind Success");
            mCalculate = Calculate.asInterface(service);
            try {
                mCalculate.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCalculate = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_binder);
        ButterKnife.bind(this);
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unbindService(mConnection);
    }

    @OnClick({R.id.custom_add_btn,R.id.custom_sub_btn})
    public void onClickButton(View v){
        if (mCalculate == null){
            return;
        }
        switch (v.getId()){
            case R.id.custom_add_btn:
                try {
                    Toast.makeText(this,String.valueOf(mCalculate.add(6,2)),Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.custom_sub_btn:
                try {
                    Toast.makeText(this,String.valueOf(mCalculate.sub(6,2)),Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void bindService(){
        Intent intent = new Intent("com.ljd.binder.CUSTOM_BINDER");
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

}
