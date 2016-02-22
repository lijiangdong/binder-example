package com.ljd.binder.binderpool;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ljd.binder.ICalculate;
import com.ljd.binder.IRect;
import com.ljd.binder.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BinderPoolActivity extends AppCompatActivity {

    private final String TAG = "BinderPoolActivity";
    private final int ADD_BUTTON = 1001;
    private final int SUB_BUTTON = 1002;
    private final int AREA_BUTTON = 1003;
    private final int PER_BUTTON = 1004;

    private ICalculate mCalculate;
    private IRect mRect;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what){
                    case ADD_BUTTON:
                        mCalculate = ICalculateImpl.asInterface((IBinder) msg.obj);
                        Log.e(TAG,String.valueOf(mCalculate.add(3,2)));
                        Toast.makeText(BinderPoolActivity.this,String.valueOf(mCalculate.add(3,2)),Toast.LENGTH_SHORT).show();
                        break;
                    case SUB_BUTTON:
                        mCalculate = ICalculateImpl.asInterface((IBinder) msg.obj);
                        Log.e(TAG,String.valueOf(mCalculate.sub(3,2)));
                        Toast.makeText(BinderPoolActivity.this,String.valueOf(mCalculate.sub(3,2)),Toast.LENGTH_SHORT).show();
                        break;
                    case AREA_BUTTON:
                        mRect = IRectImpl.asInterface((IBinder) msg.obj);
                        Log.e(TAG,String.valueOf(mRect.area(3,2)));
                        Toast.makeText(BinderPoolActivity.this,String.valueOf(mRect.area(3,2)),Toast.LENGTH_SHORT).show();
                        break;
                    case PER_BUTTON:
                        mRect = IRectImpl.asInterface((IBinder) msg.obj);
                        Log.e(TAG,String.valueOf(mRect.perimeter(3,2)));
                        Toast.makeText(BinderPoolActivity.this,String.valueOf(mRect.perimeter(3,2)),Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.add_btn,R.id.sub_btn,R.id.area_btn,R.id.per_btn})
    public void onClickButton(View v){
        switch (v.getId()){
            case R.id.add_btn:
                operate(BinderPool.BINDER_CALCULATE,ADD_BUTTON);
                break;
            case R.id.sub_btn:
                operate(BinderPool.BINDER_CALCULATE,SUB_BUTTON);
                break;
            case R.id.area_btn:
                operate(BinderPool.BINDER_RECT,AREA_BUTTON);
                break;
            case R.id.per_btn:
                operate(BinderPool.BINDER_RECT,PER_BUTTON);
                break;
            default:
                break;
        }
    }
    private void operate(final int code,final int flag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
                IBinder binder = binderPool.queryBinder(code);
                mHandler.obtainMessage(flag,binder).sendToTarget();
//                doWork(code);
            }
        }).start();
    }

    private void doWork(int code){
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
