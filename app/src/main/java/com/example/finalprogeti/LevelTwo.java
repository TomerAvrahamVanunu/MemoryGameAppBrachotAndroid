package com.example.finalprogeti;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LevelTwo extends AppCompatActivity implements View.OnClickListener {

    /** Exit the game button */
    private Button btExit;
    /** Start the game button */
    private ImageButton btStart;
    /** Restart the game button */
    private ImageButton btRestart;
    /** Start instructions Text */
    private TextView tvStart;
    /** The two players names */
    private Bundle playerNames;
    /** Text of the turn of which player */
    private TextView tvPlayerTurn;
    /** Stats of the 1st player */
    private TextView tvFirstPlayerStats;
    /** Stats of the 2nd player */
    private TextView tvSecondPlayerStats;
    /** "Game Over" image view */
    private ImageView ivGameOver;
    /** Random variable to shuffle the pictures in the deck */
    private Random rand = new Random();
    /** Static variable for the player turn */
    private static int playerTurn;
    /** Array which contains all the images (parallel to the deck itself) */
  //  private ImageView[] deckArr = new ImageView[17];
    /** Array which contains the picture of each card */
 //   private int[] rArray = new int[17];
    /** Where is the 1st card which is chosen */
    private int cardOne = 1;
    /** Where is the 2nd card which is chosen */
    private int cardTwo = 1;
    /** Which card is pressed (1st or 2nd) */
    private int pressNum = 1;
    /** Boolean variable if the game has started or not */
    private boolean gameStart = false;
    /** Boolean variable if the game has ended or not */
    private boolean gameFlag = true;
    /** Boolean variable if it's the 1st player turn */
    private boolean firstPlayerFlag;
    /** Boolean variable if it's the 2nd player turn */
    private boolean secondPlayerFlag;
    /** 1st player (red player) */
  //  private PlayerTwo firstPlayer;
    /** 2nd player (blue player) */
  //  private PlayerTwo secondPlayer;
    /** Knock-Out sound */
    private MediaPlayer knockout;
    /** Finish him sound */
    private MediaPlayer finishhim;
    /** Intent of the music */
    private Intent musicIntent;
    /** Intent of the vibration */
    private Intent vibrateintent;
    Controler controlr = new Controler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_two);

        btStart = findViewById(R.id.btStart);
        btRestart = findViewById(R.id.btRestart);
        tvStart = findViewById(R.id.tvStart);
        btExit = findViewById(R.id.btReturn);
        tvPlayerTurn=findViewById(R.id.tvPlayerTurn);
        tvFirstPlayerStats=findViewById(R.id.tvFirstPlayerStats);
        tvSecondPlayerStats=findViewById(R.id.tvSecondPlayerStats);
        ivGameOver = findViewById(R.id.ivGameOver);
        ivGameOver.setVisibility(View.INVISIBLE); // Make the "game Over" image invisible
        Intent i = getIntent(); // The intent itself
        playerNames = i.getExtras(); // The players from the previous layout
        String firstPlayerName = playerNames.getString("First Player Name"); // 1st (red) player name
        String bluePlayerName = playerNames.getString("Second Player Name"); // 2nd (blue) player name
        controlr.model.setFirstPlayer(new PlayerTwo(firstPlayerName)); // New playerTwo with the 1st (red) player name
        controlr.model.setSecondPlayer( new PlayerTwo(bluePlayerName)); // New playerTwo with the 2nd (blue) player name
        tvFirstPlayerStats.setText(""+ controlr.model.getFirstPlayer().getName()+": "+ controlr.model.getFirstPlayer().getPairs()); // Give the 1st (red) player stats the base of it's name and 0 pairs
        tvSecondPlayerStats.setText(""+ controlr.model.getSecondPlayer().getName()+": "+ controlr.model.getSecondPlayer().getPairs()); // Give the 2nd (blue) player stats the base of it's name and 0 pairs
        knockout = MediaPlayer.create(this, R.raw.knockout); // get the sound
        finishhim = MediaPlayer.create(this, R.raw.finishhim); // get the sound
        musicIntent = new Intent(this, BackgroundMusic.class); // get the sound
        vibrateintent = new Intent(getApplicationContext(), BroadcastVibration.class); // Make a vibration intent
        vibrateintent.setAction("Vibrate"); // Set it's action
        vibrateintent.putExtra("Length", 100); // Add the vibration itself

        controlr.inItRarray(); // Put pictures in the array
        inItDeck(); // make the cards on the deck and the pictures in the Rarray parallel
        controlr.DisableAllCards(); // Disable all the cards until the game starts
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

    public void Start(View view) /** Start the game*/
    {
        tvStart.setVisibility(View.INVISIBLE); // Disappear the start instructions text
        btStart.setEnabled(false); // Can't start the game more than once therefore this button is disabled
        int startPlayer = rand.nextInt(2) + 1; // Random player begins (1 = 1st player, 2 = 2nd player)
        playerTurn = startPlayer; // The player turn is the player who started
        if (playerTurn == 1)  // 1st (red) player begins
        {
            changeState(1);
            ChangeCurrentPlayerPic((R.drawable.red)); // Change the player turn picture to red
        }
        else // 2nd (blue) player begins
        {
            changeState(2);
            ChangeCurrentPlayerPic((R.drawable.blue)); // Change the player turn picture to blue
        }
        gameStart = true;
        controlr.EnableCards(); // Make the cards ready to be played
    }

    /** Restart the game alert dialog */
    public void Restart(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to play again?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PlayAgain();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    /** Exit the game alert dialog */
    public void Exit(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GoToMenu();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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

    /** Finish the game */
    private void GameHasEnded()
    {
        ivGameOver.setVisibility(View.VISIBLE); // Show the "Game over" image
        changeState(0);
        Toast.makeText(LevelTwo.this, "Game Has Ended!", Toast.LENGTH_SHORT).show();
        knockout.start(); // Make the knock-Out noise
    }


    /** Transfer every picture into the deck itself and make sure it's ready to be clicked */
    private void inItDeck() /** Transfer every picture into the deck itself and make sure it's ready to be clicked */
    {
        controlr.model.getDeckArr()[1] = findViewById(R.id.ivBack101);
        controlr.model.getDeckArr()[2] = findViewById(R.id.ivBack102);
        controlr.model.getDeckArr()[3] = findViewById(R.id.ivBack103);
        controlr.model.getDeckArr()[4] = findViewById(R.id.ivBack104);
        controlr.model.getDeckArr()[5] = findViewById(R.id.ivBack201);
        controlr.model.getDeckArr()[6] = findViewById(R.id.ivBack202);
        controlr.model.getDeckArr()[7] = findViewById(R.id.ivBack203);
        controlr.model.getDeckArr()[8] = findViewById(R.id.ivBack204);
        controlr.model.getDeckArr()[9] = findViewById(R.id.ivBack301);
        controlr.model.getDeckArr()[10] = findViewById(R.id.ivBack302);
        controlr.model.getDeckArr()[11] = findViewById(R.id.ivBack303);
        controlr.model.getDeckArr()[12] = findViewById(R.id.ivBack304);
        controlr.model.getDeckArr()[13] = findViewById(R.id.ivBack401);
        controlr.model.getDeckArr()[14] = findViewById(R.id.ivBack402);
        controlr.model.getDeckArr()[15] = findViewById(R.id.ivBack403);
        controlr.model.getDeckArr()[16] = findViewById(R.id.ivBack404);
        for (int i = 1; i < 17; i++)
            controlr.model.getDeckArr()[i].setOnClickListener(this); // Make every card clickable
    }


    /** Disable all the cards until the game starts/continues */
 /*   private void DisableAllCards()
    {
        for (int i = 1; i < deckArr.length; i++) {
            deckArr[i].setEnabled(false);
        }
    }

    /** Enable all the cards which still on the board */
  /*  private void EnableCards()
    {
        for (int i = 1; i < deckArr.length; i++) {
            if (deckArr[i].getVisibility() == View.VISIBLE) // If the card is still visible visible
                deckArr[i].setEnabled(true); // Make it enable and ready to be pressed
        }
    }

    /** Gets the place which is pressed in the deck and return the place (the chosen card) */
    public int findIndexPress(View view)
    {
        int pressIndex = 0; // the place in the array which is pressed
        for (int i = 1; i < controlr.model.getDeckArr().length; i++) {
            if (controlr.model.getDeckArr()[i].getId() == view.getId())
                pressIndex = i;
        }
        return pressIndex; // return the place of the selected card
    }

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
        return controlr.model.getFirstPlayer().getPairs()+ controlr.model.getSecondPlayer().getPairs() != 8; // if the sum of the players pairs is the sum of all of the 8 pairs
    }

    /** Change the color of the current player turn (red or blue) */
    private void ChangeCurrentPlayerPic(int id)
    {
        btStart.setImageResource(id);
    }

    /** Update the number of pairs that each player has */
    private void updateGameStats()
    {
        tvFirstPlayerStats.setText(""+ controlr.model.getFirstPlayer().getName()+": "+ controlr.model.getFirstPlayer().getPairs());
        tvSecondPlayerStats.setText(""+ controlr.model.getSecondPlayer().getName()+": "+ controlr.model.getSecondPlayer().getPairs());
    }

    /** Play the game as it's one player for the specific playing player */
    private void PlayTheGame(View view, final PlayerTwo player)
    {
        if (pressNum == 1) // the 1st card is chosen
        {
            cardOne = findIndexPress(view); // Find the place of the first card
            controlr.model.getDeckArr()[cardOne].setImageResource(controlr.model.getrArray()[cardOne]); // Flip the card (change it's image)
            controlr.model.getDeckArr()[cardOne].setEnabled(false); // Make sure it cannot be pressed again as long as it's flipped
            pressNum = 2; // Go the the 2nd card pick
        }
        else // the 2nd card is chosen (pressNum = 2)
        {
            cardTwo = findIndexPress(view); // Find the place of the second card
            controlr.model.getDeckArr()[cardTwo].setImageResource(controlr.model.getrArray()[cardTwo]); // Flip the card (change it's image to the parallel picture)
            controlr.model.getDeckArr()[cardTwo].setEnabled(false); // Make sure it cannot be pressed again as long as it's flipped
            controlr.DisableAllCards(); // Cancel the option to choose other cards as long as the check ot the two cards is going
            new Handler().postDelayed(new Runnable() // Handler (thread) of 0.75 seconds that "stops" the game to check if the cards are the same ot not
            {
                @Override
                public void run() // The method of the handler (thread)
                {
                    boolean isSame = checkIfSame(controlr.model.getDeckArr()[cardOne], controlr.model.getDeckArr()[cardTwo]); // A boolean variable which contains if the cards are the same or not
                    if (isSame) // Cards are the same
                    {
                        getApplicationContext().sendBroadcast(vibrateintent); // Make the phone vibrate
                        player.oneMorePoint(); // Increase the player who is playing points by one
                        controlr.model.getDeckArr()[cardOne].setVisibility(View.INVISIBLE); // Disappear the 1st card
                        controlr.model.getDeckArr()[cardTwo].setVisibility(View.INVISIBLE); // Disappear the 2nd card
                        updateGameStats(); // Update the game stats
                        gameFlag = GameIsStillGoing(); // Check if the game has ended or not
                    }
                    else // Cards aren't the same
                    {
                        controlr.model.getDeckArr()[cardOne].setImageResource(R.drawable.cardbackpic); // Flip the card again to it's back
                        controlr.model.getDeckArr()[cardTwo].setImageResource(R.drawable.cardbackpic); // Flip the card again to it's back
                        if (playerTurn == 1) // First player turn has ended
                        {
                            // Get ready to the 2nd player turn
                            playerTurn = 2;
                            changeState(playerTurn);
                            firstPlayerFlag = false;
                            ChangeCurrentPlayerPic((R.drawable.blue));
                        }
                        else // Second player turn has ended
                        {
                            // Get ready to the 1st player turn
                            playerTurn = 1;
                            changeState(playerTurn);
                            secondPlayerFlag = false;
                            ChangeCurrentPlayerPic((R.drawable.red));
                        }
                    }
                    if(!gameFlag) // If game has ended
                        GameHasEnded(); // Game has ended
                    else // Game hasn't ended yet
                        {
                        if (controlr.model.getFirstPlayer().getPairs() + controlr.model.getSecondPlayer().getPairs() == 7) // There is only one more pair on the deck (one more turn)
                        {
                            if (playerTurn == 1 && controlr.model.getFirstPlayer().getPairs() > 4) // 1st player turn and he wins for sure
                                finishhim.start();
                            if (playerTurn == 2 && controlr.model.getSecondPlayer().getPairs() >= 4) // 2nd player turn and he wins for sure
                                finishhim.start();
                        }
                    }
                    controlr.EnableCards();
                }
            }, 750);
            pressNum = 1; // Go the the 1nd card pick
        }
    }

    /** Change the state of the game (which player is playing at the moment) */
    private void changeState(int player)
    {
        if (player == 1) // 1st player turn
        {
            tvPlayerTurn.setTextColor(Color.RED); // 1st player color in the player turn text
            tvPlayerTurn.setText("" + controlr.model.getFirstPlayer().getName() + "'s turn");
        } else
            if (player == 2) // 2nd player turn
        {
            tvPlayerTurn.setTextColor(Color.BLUE); // 2nd player color in the player turn text
            tvPlayerTurn.setText("" + controlr.model.getSecondPlayer().getName() + "'s turn");
        } else // Game has ended (Player == 0)
            {
            String WinOrTie; // String with the end of then game stats
            if (controlr.model.getFirstPlayer().getPairs() > controlr.model.getSecondPlayer().getPairs()) // First player has won
            {
                WinOrTie = "" + controlr.model.getFirstPlayer().getName() + " has won with " + controlr.model.getFirstPlayer().getPairs() + " pairs!";
                ChangeCurrentPlayerPic((R.drawable.red));
                tvPlayerTurn.setTextColor(Color.RED);
            }
            else {
                if (controlr.model.getFirstPlayer().getPairs() < controlr.model.getSecondPlayer().getPairs()) // Second player has won
                {
                    WinOrTie = "" + controlr.model.getSecondPlayer().getName() + " has won with " + controlr.model.getSecondPlayer().getPairs() + " pairs!";
                    ChangeCurrentPlayerPic((R.drawable.blue));
                    tvPlayerTurn.setTextColor(Color.BLUE);
                }
                else // There is a tie
                {
                    WinOrTie = "Tie!";
                    ChangeCurrentPlayerPic((R.drawable.tiepic));
                    tvPlayerTurn.setTextColor(Color.WHITE);
                }
            }
            tvPlayerTurn.setText(WinOrTie); // Show the end of the game stats
        }
    }

    /** The click on the screen (but refers to the deck)  every time- the game itself*/
    @Override
    public void onClick(View view) {
        if (playerTurn == 1) // Red player turn
        {
            firstPlayerFlag = true;
            PlayTheGame(view, controlr.model.getFirstPlayer());
            if (GameIsStillGoing()) {
                if (!firstPlayerFlag) // The 1st player turn has ended
                    playerTurn = 2; // Change the turn to the 2nd player
            }
        }
        else // Blue player turn
        {
            secondPlayerFlag = true;
            PlayTheGame(view, controlr.model.getSecondPlayer());
            if (GameIsStillGoing()) {
                if (!secondPlayerFlag) // The 2nd player turn has ended
                    playerTurn = 1; // Change the turn to the 1st player
            }
        }
    }
}