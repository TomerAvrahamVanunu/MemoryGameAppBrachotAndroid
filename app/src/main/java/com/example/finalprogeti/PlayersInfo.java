package com.example.finalprogeti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayersInfo extends AppCompatActivity {

    /** 1st player button (red player) */
    private Button btSetFirstPlayer;
    /** 2nd player button (blue player) */
    private Button btSetSecondPlayer;
    /** Previous screen button */
    private Button btBack;
    /** Play button */
    private Button btPlay;
    /** 1st Player variable */
   // private PlayerTwo FirstPlayer;
    /** 2nd player variable */
   // private PlayerTwo SecondPlayer;
    /** 1st player name flag */
    private Boolean thereIsFirstName = false;
    /** 2nd player name flag */
    private Boolean thereIsSecondName = false;
    /** Intent of the music */
    private Intent musicIntent;
    Controler controler= new Controler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

       // FirstPlayer = new PlayerTwo(""); // Reset the 1st player name
       // SecondPlayer = new PlayerTwo(""); // Reset the 2nd player name
         controler.model.intiTwoPlayer(controler.model.getFirstPlayer());
         controler.model.intiTwoPlayer(controler.model.getSecondPlayer());
        btSetFirstPlayer = findViewById(R.id.btSetFirstPlayer);
        btSetSecondPlayer = findViewById(R.id.btSetSecondPlayer);
        btBack = findViewById(R.id.btBack);
        btPlay = findViewById(R.id.btPlay);
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
    private void GoToGame()
    {
        final Intent intent = new Intent(this, LevelTwo.class);
        intent.putExtra("First Player Name", controler.model.getFirstPlayer().getName()); // Add the 1st player name
        intent.putExtra("Second Player Name", controler.model.getSecondPlayer().getName()); // Add the 2nd player name
        startActivity(intent);
    }

    /** Go to the previous screen */
    public void GoBack(View view)
    {
        final Intent intent = new Intent(this, LevelChoice.class);
        startActivity(intent);
    }

    /** Play button */
    public void Play(View view)
    {
        // Make sure that there are 2  valid names and if not, go back to the players info screen
        if (BothNamesAreValid())
            GoToGame();
        else
            Toast.makeText(PlayersInfo.this, "Names aren't valid!", Toast.LENGTH_SHORT).show();
    }

    /** Update the 1st player information */
    public void ChangeFirstPlayer(View view)
    {
        ChangePlayer(1);
    }

    /** Update the 2nd player information */
    public void ChangeSecondPlayer(View view)
    {
        ChangePlayer(2);
    }

    /** Change the selected player information due to the given name */
    private void ChangePlayer(final int Player)
    {
        Toast.makeText(PlayersInfo.this, "Please enter your name", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Player == 1) // Title that fits the 1st player
            builder.setTitle("First player name:");
        else // Title that fits the 2nd player
            builder.setTitle("Second player name:");
        final EditText input = new EditText(PlayersInfo.this);
        LinearLayout.LayoutParams size = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(size);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Name = input.getText().toString(); // The name of the player
                if (Player == 1) // The 1st player (red player) is selected
                {
                    if (Name.length() < 1) // If no name was written
                    {
                        thereIsFirstName = false;
                        Toast.makeText(PlayersInfo.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        String firstLetter = Name.substring(0, 1);
                        if (firstLetter.equals(" ")) // If the name starts with space
                        {
                            thereIsFirstName = false;
                            Toast.makeText(PlayersInfo.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                        }
                        else // Name is valid
                            {
                            btSetFirstPlayer.setText(Name); // Show the name of the 1st player
                            controler.model.getFirstPlayer().setName(Name); // Set the 1st player name
                            thereIsFirstName = true; // Change the name flag to true
                        }
                    }
                }
                else // The 1st player (red player) is selected
                    {
                    if (Name.length() < 1) // If no name was written
                    {
                        thereIsSecondName = false;
                        Toast.makeText(PlayersInfo.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                    } else {
                        String firstLetter = Name.substring(0, 1);
                        if (firstLetter.equals(" ")) // If the name starts with space
                        {
                            thereIsSecondName = false;
                            Toast.makeText(PlayersInfo.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                        } else // Name is valid
                            {
                            btSetSecondPlayer.setText(Name); // Show the name of the 2nd player
                            controler.model.getSecondPlayer().setName(Name); // Set the 2nd player name
                            thereIsSecondName = true; // Change the name flag to true
                        }
                    }
                }
            }
        });
        builder.show();
    }

    /** Returns true if both names are valid, else returns false */
    private boolean BothNamesAreValid()
    {
        if (!thereIsFirstName || !thereIsSecondName)
            return false;
        return true;
    }
}
