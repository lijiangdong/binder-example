package com.ljd.binder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ljd.binder.binderpool.BinderPoolActivity;
import com.ljd.binder.custombinder.CustomBinderActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.binder_pool_btn,R.id.custom_binder_btn})
    public void onClickButton(View v){
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.binder_pool_btn:
                intent.setClass(this,BinderPoolActivity.class);
                startActivity(intent);
                break;
            case R.id.custom_binder_btn:
                intent.setClass(this,CustomBinderActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
