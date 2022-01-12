package com.example.finalprogeti;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusic extends Service implements MediaPlayer.OnCompletionListener {

    /** Song number 1*/
    private MediaPlayer ribo1;
    /** Song number 2*/
    private MediaPlayer ribo2;
    /** Song number 3*/
    private MediaPlayer ribo3;
    /** static variable that contains the number of the song which is playing at the moment*/
    public static int songNumber = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        /** get the 1st song (Bracha Ad Bli Dai - by Hot Yshai Ribo) */
        ribo1 = MediaPlayer.create(this, R.raw.ribo1);
        ribo1.setOnCompletionListener(this);
        /** get the 2nd song (Under The Bridge by Red Hot Chili Peppers) */
        ribo2 = MediaPlayer.create(this, R.raw.ribo2);
        ribo2.setOnCompletionListener(this);
        /** get the third song (Snow by Red Hot Chili Peppers) */
        ribo3 = MediaPlayer.create(this, R.raw.ribo3);
        ribo3.setOnCompletionListener(this);

    }

    /** Change the played song due to the static variable */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (songNumber == 1) {
            if (ribo3.isPlaying())
                ribo3.stop();
            if (ribo2.isPlaying())
                ribo2.stop();
            if (!ribo1.isPlaying())
                ribo1.start();
        }
        if (songNumber == 2) {
            if (ribo3.isPlaying())
                ribo3.stop();
            if (ribo1.isPlaying())
                ribo1.stop();
            if (!ribo2.isPlaying())
                ribo2.start();
        }
        if (songNumber == 3) {
            if (ribo1.isPlaying())
                ribo1.stop();
            if (ribo2.isPlaying())
                ribo2.stop();
            if (!ribo3.isPlaying())
                ribo3.start();
        }
        return START_STICKY;
    }

    /** Stop playing the current song (destroy the intent)*/
    public void onDestroy()
    {
        if(songNumber == 1) {
            if (ribo1.isPlaying()) {
                ribo1.stop();
            }
        }
        if(songNumber == 2) {
            if (ribo2.isPlaying()) {
                ribo2.stop();
            }
        }
        if(songNumber == 3) {
            if (ribo3.isPlaying()) {
                ribo3.stop();
            }
        }
        ribo1.release();
    }

    public void onCompletion(MediaPlayer _mediaPlayer) {
        stopSelf();
    }
}