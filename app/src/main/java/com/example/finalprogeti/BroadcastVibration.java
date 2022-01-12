package com.example.finalprogeti;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class BroadcastVibration extends BroadcastReceiver {

    /** During the vibration */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        int Length = intent.getExtras().getInt("Length");
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null; // Check that there is a value in the vibrator
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) // Check the current device SDK for the vibration which fits it
            vibrator.vibrate(VibrationEffect.createOneShot(Length, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibrator.vibrate(Length);
    }
}
