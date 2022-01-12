package com.example.finalprogeti;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class LavelOne extends AppCompatActivity implements View.OnClickListener {

    /**Exit the game button*/
    private Button btExit;
    /**Timer which contains the player time (his score)*/
    private Chronometer timer;
    /**Start the game button*/
    private ImageButton btStart;
    /**Restart the game button*/
    private ImageButton btRestart;
    /**Save the score of the player button*/
    private ImageButton btSaveScore;
    /**Start instructions Text*/
    private TextView tvStart;
    /**Exit the game button*/
    private TextView tvScore;
    /**How many pairs has the player achieved*/
    private TextView tvPairs;
    /**"Game Over" image view*/
    private ImageView ivGameOver;
    /**Array which contains all the images (parallel to the deck itself)*/
   // private ImageView[] deckArr = new ImageView[17];

    /** Where is the 1st card which is chosen*/
    private int cardOne = 1;
    /**Where is the 2nd card which is chosen*/
    private int cardTwo = 1;
    /**Which card is pressed (1st or 2nd)*/
    private int pressNum = 1;

    /**Name of the player who is saved*/
    //private String FileName;
    /**Time of the player who is saved*/
    //private String FileTime;
    /**Golf clap sound*/
    private MediaPlayer golfclap;
    /**Audience clap sound*/
    private MediaPlayer audienceclap;
    /**Intent of the music*/
    private Intent musicIntent;
    /**Intent of the vibration*/
    private Intent vibrateintent;
    Controler controler= new Controler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);

        btStart = findViewById(R.id.btStart);
        btExit = findViewById(R.id.btReturn);
        timer = findViewById(R.id.timer);
        tvStart = findViewById(R.id.tvStart);
        tvScore = findViewById(R.id.tvScore);
        tvPairs = findViewById(R.id.tvPairs);
        btRestart = findViewById(R.id.btRestart);
        btSaveScore = findViewById(R.id.btSaveScore);
        ivGameOver = findViewById(R.id.ivGameOver);
        tvScore.setVisibility(View.INVISIBLE); // Make the score text invisible
        tvPairs.setVisibility(View.INVISIBLE); // Make the number of pairs invisible
        btSaveScore.setVisibility(View.INVISIBLE); // Make the score button invisible
        ivGameOver.setVisibility(View.INVISIBLE); // Make the "game Over" image invisible
        golfclap = MediaPlayer.create(this, R.raw.golfclap); // get the sound
        audienceclap = MediaPlayer.create(this, R.raw.audienceclap); // get the sound
        musicIntent = new Intent(this, BackgroundMusic.class); // get the sound
        vibrateintent = new Intent(getApplicationContext(), BroadcastVibration.class); // Make a vibration intent
        vibrateintent.setAction("Vibrate"); // Set it's action
        vibrateintent.putExtra("Length", 100); // Add the vibration itself
        try {
            controler= new Controler(openFileOutput("Players_File", MODE_APPEND));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        controler.model.initPlayer();
        controler.inItRarray(); // Put pictures in the array
        inItDeck(); // make the cards on the deck and the pictures in the Rarray parallel
        controler.DisableAllCards(); // Disable all the cards until the game starts
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    /** Switch the current song in the background */
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

    /** Save the player name and score (Press the button itself) */
    public void SaveScore(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to save your score?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) // The player has chosen to save his score
                    {
                        SaveScore();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /** Exit the game alert dialog*/
    public void Exit(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) // The player has chosen to exit the game
                    {
                        GoToMenu();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /** Restart the game alert dialog */
    public void Restart(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to play again?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)// The player has chosen to play again (restart the game)
                    {
                        PlayAgain();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /** Start the game */
    public void StartTheGame(View view)
    {
        btStart.setEnabled(false); // Can't start the game more than once therefore this button is disabled
        btStart.setVisibility(View.INVISIBLE); // Disappear the start button
        tvStart.setVisibility(View.INVISIBLE); // Disappear the start instructions text
        tvScore.setVisibility(View.VISIBLE); // Appear the "Score:" Text
        tvPairs.setVisibility(View.VISIBLE); // Appear the number of pairs that the player has found
        tvPairs.setText("Pairs: " + controler.model.getPairs()); // Player's pairs frame
        controler.EnableCards(); // Make all the cards pressable
        timer.setBase(SystemClock.elapsedRealtime()); // Give the timer it's base of 00:00
        timer.start(); // Start the timer
    }



    /** Save the player name and score */
    private void SaveScore()
    {
        //player = new Player("No name yet", "");  // Create a new player with no information yet
        controler.model.initPlayer();
        Toast.makeText(LavelOne.this, "Please enter your name", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Make an alert dialog
        builder.setTitle("Player name:");
        final EditText input = new EditText(LavelOne.this); // the name which is being input
        LinearLayout.LayoutParams size = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(size);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Name = input.getText().toString(); // The name of the player
                if (Name.length() < 1) // If no name was written
                    Toast.makeText(LavelOne.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                 else {
                    String firstLetter = Name.substring(0, 1);
                    if (firstLetter.equals(" ")) // If the name starts with space
                        Toast.makeText(LavelOne.this, "Name not valid!", Toast.LENGTH_SHORT).show();
                     else {
                         controler.model.getPlayer().setName(Name); // Set the "no information player"'s name
                         controler.model.getPlayer().setTime( controler.model.getTime()); // Set the "no information player"'s time
                        SavePlayer();

                    }
                }
            }
        });
        builder.show();
    }

    /** Save the player and his score in the file and go to the Scores screen */
    private void SavePlayer()
    {

        controler.fileoutName();
       Toast.makeText(LavelOne.this, "" + controler.model.getPlayer().getName() + " with " + controler.model.getPlayer().getTime() + " seconds", Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(this, Score.class);
       startActivity(intent); // Go to the Scores screen
    }

    /** Go to menu screen */
    private void GoToMenu()
    {
        final Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }

    /** Play again (restart the game) */
    private void PlayAgain()
    {
        Intent playAgainIntent = getIntent();
        finish();
        startActivity(playAgainIntent);
    }



    private void inItDeck() /** Transfer every picture into the deck itself and make sure it's ready to be clicked */
    {
        controler.model.getDeckArr()[1] = findViewById(R.id.ivBack101);
        controler.model.getDeckArr()[2] = findViewById(R.id.ivBack102);
        controler.model.getDeckArr()[3] = findViewById(R.id.ivBack103);
        controler.model.getDeckArr()[4] = findViewById(R.id.ivBack104);
        controler.model.getDeckArr()[5] = findViewById(R.id.ivBack201);
        controler.model.getDeckArr()[6] = findViewById(R.id.ivBack202);
        controler.model.getDeckArr()[7] = findViewById(R.id.ivBack203);
        controler.model.getDeckArr()[8] = findViewById(R.id.ivBack204);
        controler.model.getDeckArr()[9] = findViewById(R.id.ivBack301);
        controler.model.getDeckArr()[10] = findViewById(R.id.ivBack302);
        controler.model.getDeckArr()[11] = findViewById(R.id.ivBack303);
        controler.model.getDeckArr()[12] = findViewById(R.id.ivBack304);
        controler.model.getDeckArr()[13] = findViewById(R.id.ivBack401);
        controler.model.getDeckArr()[14] = findViewById(R.id.ivBack402);
        controler.model.getDeckArr()[15] = findViewById(R.id.ivBack403);
        controler.model.getDeckArr()[16] = findViewById(R.id.ivBack404);
        for (int i = 1; i < 17; i++)
            controler.model.getDeckArr()[i].setOnClickListener(this); // Make every card clickable
    }

    /** Gets the place which is pressed in the deck and return the place (the chosen card) */
    public int findIndexPress(View view)
    {
        int pressIndex = 0; // the place in the array which is pressed
        for (int i = 1; i < controler.model.getDeckArr().length; i++) {
            if (controler.model.getDeckArr()[i].getId() == view.getId())
                pressIndex = i;
        }
        return pressIndex; // return the place of the selected card
    }

    @Override
    /** The click on the screen (but refers to the deck)  every time- the game itself*/
    public void onClick(View view)
    {
        if (pressNum == 1) // the 1st card is chosen
        {
            cardOne = findIndexPress(view); // Find the place of the first card
            controler.model.getDeckArr()[cardOne].setImageResource(controler.model.getrArray()[cardOne]); // Flip the card (change it's image)
            controler.model.getDeckArr()[cardOne].setEnabled(false); // Make sure it cannot be pressed again as long as it's flipped
            pressNum = 2; // Go the the 2nd card pick
        }
        else // the 2nd card is chosen (pressNum = 2)
        {
            cardTwo = findIndexPress(view); // Find the place of the second card
            controler.model.getDeckArr()[cardTwo].setImageResource(controler.model.getrArray()[cardTwo]); // Flip the card (change it's image to the parallel picture)
            controler.model.getDeckArr()[cardTwo].setEnabled(false); // Make sure it cannot be pressed again as long as it's flipped
            controler.DisableAllCards(); // Cancel the option to choose other cards as long as the check ot the two cards is going
            new Handler().postDelayed(new Runnable() // Handler (thread) of 0.75 seconds that "stops" the game to check if the cards are the same ot not
            {
                @SuppressLint("MissingPermission")
                @Override
                public void run() // The method of the handler (thread)
                {
                    boolean isSame = checkIfSame(controler.model.getDeckArr()[cardOne], controler.model.getDeckArr()[cardTwo]); // A boolean variable which contains if the cards are the same or not
                    if (isSame) // Cards are the same
                    {
                        controler.model.getDeckArr()[cardOne].setVisibility(View.INVISIBLE); // Disappear the 1st card
                        controler.model.getDeckArr()[cardTwo].setVisibility(View.INVISIBLE); // Disappear the 2nd card
                        updateGameStats(); // Update the game stats
                        getApplicationContext().sendBroadcast(vibrateintent); // Make the phone vibrate
                    }
                    else // Cards aren't the same
                    {
                        controler.model.getDeckArr()[cardOne].setImageResource(R.drawable.cardbackpic); // Flip the card again to it's back
                        controler.model.getDeckArr()[cardTwo].setImageResource(R.drawable.cardbackpic); // Flip the card again to it's back
                    }
                    if(GameIsStillGoing())
                        controler.EnableCards();
                    else
                        GameHasEnded();
                }
            }, 750);
            pressNum = 1; // Go the the 1nd card pick
        }
    }

    /** Change the game stats when a new pair is found */
    private void updateGameStats()
    {
        controler.addonePairs(); // Increase the number of pairs by 1
        tvPairs.setText("Pairs: " + controler.model.getPairs()); // Change what is written in the Pairs Text due to the change
    }

    /** Finish the game */
    private void GameHasEnded()
    {
        ivGameOver.setVisibility(View.VISIBLE); // Show the "Game over" image
        timer.stop(); // Stop the timer
        controler.model.setTime( timer.getText().toString()); // Put the time of the player in a string
        if(SystemClock.elapsedRealtime() - timer.getBase()<25000) //If the player finished the game in less than 25 seconds
            audienceclap.start(); // encourage with audience clap
        else //If the player finished the game in more than 25 seconds
            golfclap.start(); // encourage with golf clap
        Toast.makeText(LavelOne.this, "You did "+ controler.model.getTime()+" seconds. Nice!", Toast.LENGTH_SHORT).show();
        btSaveScore.setVisibility(View.VISIBLE); // Appear the option to save the score
    }

    /** Disable all the cards until the game starts/continues */
   /* private void DisableAllCards()
    {
        for (int i = 1; i < deckArr.length; i++) {
            deckArr[i].setEnabled(false);
        }
    }
   */

    /** Enable all the cards which still on the board */
    /*
    private void EnableCards()
    {
        for (int i = 1; i < deckArr.length; i++) {
            if (deckArr[i].getVisibility() == View.VISIBLE) // If the card is still visible visible
                deckArr[i].setEnabled(true); // Make it enable and ready to be pressed
        }
    }
    */

    /** If the two pictures are the same (same return true, else returns false) */
    private boolean checkIfSame(ImageView x, ImageView y)
    {
        Bitmap bitmap1 = ((BitmapDrawable) x.getDrawable()).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) y.getDrawable()).getBitmap();
        if (bitmap1 == bitmap2)
            return true; // They are the same
        return false; // They aren't the same
    }

    /** Check if the game has ended */
    private boolean GameIsStillGoing()
    {
        return controler.model.getPairs() != 8; // If the player has found all of the 8 pairs
    }
}