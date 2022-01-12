package com.example.finalprogeti;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Score extends AppCompatActivity {

    /** Return to previous screen button */
    private Button btReturn;

    /**String with all the players names and scores*/
    private String FileScore;
    /** List-view which shows all the saved information of the players */
    private ListView lv;

    /** Adapter from thr list of the players to the list-view */
    private PlayersScoreAdapter PlayersScoreAdapter;
    /** Intent of the music */
    private Intent musicIntent;
    Controler controler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btReturn = findViewById(R.id.btReturn);
        lv = findViewById(R.id.lv);
        //playerList = new ArrayList<Player>();
        try {
            controler= new Controler(openFileInput("Players_File"));
          //  controler.setIn(openFileInput("Players_File")); // Find the file with it's name
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        controler.OpenFile();
        PlayersScoreAdapter = new PlayersScoreAdapter(this, 0, 0, controler.getPlayerList());
        lv.setAdapter(PlayersScoreAdapter);
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


    /** Go to menu screen */
    public void Return(View view)
    {
        final Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }
}