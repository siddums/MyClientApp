package com.sd_xcod.myclientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sd_xcod.aidlserver.IAIDLColorInteface;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    IAIDLColorInteface iAIDLColorService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAIDLColorService = IAIDLColorInteface.Stub.asInterface(service);
            Log.i(TAG," Service is connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG," Service is disconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("AIDLColorService");
        intent.setPackage("com.sd_xcod.aidlserver");
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(iAIDLColorService!=null){
                        int color = iAIDLColorService.getColor();
                        v.setBackgroundColor(color);
                    }else{
                        Log.d(TAG," Service not initialized ");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}