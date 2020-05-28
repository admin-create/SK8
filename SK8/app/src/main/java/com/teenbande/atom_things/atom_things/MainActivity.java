package com.teenbande.atom_things.atom_things;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = SkettyControl.class.getSimpleName();
    private final SkettyControl mSkettyCont = new SkettyControl();
    private final GattServer mGattServer = new GattServer();
    private AwesomeSpeedState mAwesomeSpeedState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAwesomeSpeedState = new AwesomeSpeedState(this);

        try {
            mSkettyCont.onCreate();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mGattServer.onCreate(this, new GattServer.GattServerListener() {
            @Override
            public byte[] onSpeedRead() {
                return Ints.toByteArray(mAwesomeSpeedState.getSpeedValue());
            }

            @Override
            public void onInteractorWritten(int Wspeed) {
                int count = mAwesomeSpeedState.incrementSpeedValue(Wspeed);
                try {
                    mSkettyCont.moveToSpeed(Wspeed);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGattServer.onDestroy();
        mSkettyCont.onDestroy();
    }
}
