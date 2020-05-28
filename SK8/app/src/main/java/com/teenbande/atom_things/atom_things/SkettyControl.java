package com.teenbande.atom_things.atom_things;

import android.util.Log;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;
import java.io.IOException;
import java.util.List;

public class SkettyControl {
    private int startup = 0;
    private  Thread MT;
    private static final String TAG = SkettyControl.class.getSimpleName();

    public void initializePwm() throws IOException {
        MT.start();
    }

    public void onCreate() throws IOException {
        MT = new Thread(new MotorRunner());
    }

    public void moveToSpeed(int wSpeed) throws IOException {

        if(wSpeed == 0){
            if(startup == 1){
                MT.stop();

            }
            else {
                initializePwm();
                startup++;
            }
        }
        else{
            MotorRunner.GetSpeed = 5+(wSpeed*0.47);
        }
        Log.i(TAG, "PWM will catch upto " + MotorRunner.GetSpeed, null);

    }

    public void onDestroy() {
        MT.stop();
    }
}

class MotorRunner implements Runnable {

    private volatile static double ThreadSpeed = 5;
    public static double GetSpeed = 5.50;
    public Pwm mPwm;
    public double INC = 0.1;
    PeripheralManagerService service = new PeripheralManagerService();
    private static final String PWM_NAME = "PWM0";
    private static final String TAG = SkettyControl.class.getSimpleName();
    private double initSpeed = 9.7;
    private int freq = 50;


    public MotorRunner () {

        List<String> portList = service.getPwmList();
        if (portList.isEmpty()) {
            Log.e(TAG, "No PWM port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);
        }
        // Attempt to access the PWM port
        try {
            mPwm = service.openPwm(PWM_NAME);
        } catch (IOException e) {
            Log.w(TAG, "Unable to access PWM", e);
        }

        try {
            mPwm.setPwmFrequencyHz(freq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPwm.setPwmDutyCycle(initSpeed);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPwm.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(double calP = 9.7 ;calP>0.50;calP=calP-0.47)
        // Enable the PWM signal
        {

            try {
                mPwm.setPwmDutyCycle(calP);
                java.lang.Thread.sleep(100);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Log.i(TAG,"Current ThreadSpeed: " + ThreadSpeed);
        Log.i(TAG,"Current GetSpeed: " + GetSpeed);
    }

    @Override
    public void run() {
            Log.i(TAG, "inside the run method");
            while(true) {
                try {

                    mPwm.setPwmDutyCycle(ThreadSpeed);
                    if (ThreadSpeed <= GetSpeed) {
                        java.lang.Thread.sleep(100);
                        ThreadSpeed = ThreadSpeed + INC;
                    }
                    else{
                        java.lang.Thread.sleep(100);
                        ThreadSpeed = ThreadSpeed - INC;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               Log.i(TAG, "Current ThreadSpeed: " + ThreadSpeed);
            }
    }

//    @Override
//    public final void stop()
//    {
//        if (mPwm != null){
//            try {
//                mPwm.close();
//                mPwm = null;
//            } catch (IOException e) {
//                Log.w(TAG, "Unable to close PWM", e);
//            }
//        }
//    }
}
