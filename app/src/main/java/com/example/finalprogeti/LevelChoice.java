package com.example.finalprogeti;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LevelChoice extends AppCompatActivity {

    private Button LevelOnebt; /** One player button */
    private Button LevelTwobt; /** Two players button */
    private Intent musicIntent; /** Intent of the music */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choice);

        LevelOnebt = findViewById(R.id.LevelOnebt);
        LevelTwobt = findViewById(R.id.LevelTwobt);
        musicIntent = new Intent(this, BackgroundMusic.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** Switch the current song in the background */
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

    /** One Player has been selected*/
    public void LevelOne(View view)
    {
        final Intent intent = new Intent(this, LavelOne.class);
        startActivity(intent);
    }

    /** Two Players has been selected*/
    public void PlayersInfo(View view)
    {
        final Intent intent = new Intent(this, PlayersInfo.class);
        startActivity(intent);
    }
}
