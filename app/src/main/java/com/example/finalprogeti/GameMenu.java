package com.example.finalprogeti;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GameMenu extends AppCompatActivity {

    /** Play button */
    private Button btPlay;
    /** Score of the players button */
    private Button btScore;
    /** How to play button */
    private Button btHowToPlay;
    /** Intent of the music */
    private Intent musicIntent;
     Controler controler= new Controler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        btPlay = findViewById(R.id.btPlay);
        btScore = findViewById(R.id.btScore);
        btHowToPlay = findViewById(R.id.btHowToPlay);
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




    /** Play the game */
    public void Play(View view)
    {
        final Intent intent = new Intent(this, LavelOne.class);
        startActivity(intent);
    }

    /** Show the player's scores */
    public void Scores(View view)
    {
        final Intent intent = new Intent(this, Score.class);
        startActivity(intent);
    }

    /** Show to game instructions */
    public void HowToPlay(View view)
    {
        final Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }
}