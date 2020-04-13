package com.bytedance.clockapplication;

import android.os.Bundle;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bytedance.clockapplication.widget.Clock;

public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private Clock mClockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);

        ClockHandlerThread cht = new ClockHandlerThread("CLOCK");
        cht.start();
    }

    public class ClockHandlerThread extends HandlerThread implements Handler.Callback{
        public static final int MSG_QUERY_SECONDS = 100;
        private Handler cHandler;//处理刷新请请求
        public ClockHandlerThread(String name){
            super(name);
        }
        public ClockHandlerThread(String name, int priority){
            super(name);
        }
        @Override
        protected void onLooperPrepared(){
            cHandler = new Handler(getLooper(), this);
            cHandler.sendEmptyMessage(MSG_QUERY_SECONDS);
        }
        @Override
        public boolean handleMessage(Message msg){
            if(msg.what == MSG_QUERY_SECONDS){
                mClockView.postInvalidate();//刷新页面
                cHandler.sendEmptyMessageDelayed(MSG_QUERY_SECONDS,1000);
            }
            return true;
        }
    }
}


