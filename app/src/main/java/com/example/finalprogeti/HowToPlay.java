package com.example.finalprogeti;

import android.content.Intent;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HowToPlay extends AppCompatActivity {

    /** Return to previous screen button */
    private Button btReturn;
    /** Game instructions text */
    private TextView textView;
    /**  Intent of the music */
    private Intent musicIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        Button btReturn = findViewById(R.id.btReturn); // Return button
        TextView textView = findViewById(R.id.textView); // The instructions
        textView.setMovementMethod(new ScrollingMovementMethod()); // make the instructions scrollable
        musicIntent = new Intent(this, BackgroundMusic.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** Switch the current song in the background*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id) {
            case R.id.Ribo1Menu:
                BackgroundMusic.songNumber = 1;
                startService(musicIntent);
                return true;
            case R.id.Ribo2Menu:
                BackgroundMusic.songNumber = 2;
                startService(musicIntent);
                return true;
            case R.id.Ribo3Menu:
                BackgroundMusic.songNumber = 3;
                startService(musicIntent);
                return true;
            case R.id.StopMenu:
                stopService(musicIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Return to the menu */
    public void Return(View view)
    {
        final Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }
}
