package com.example.finalprogeti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    /** Intent of the first background music */
    private Intent startMusicIntent;

    /** Create the 3 seconds splash screen */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startMusicIntent = new Intent(this, BackgroundMusic.class);
        startService(startMusicIntent); // Start the background music
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this,GameMenu.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
            /* Wait 3 seconds before moving to the next screen
             */
        }, 5000);
    }
}
