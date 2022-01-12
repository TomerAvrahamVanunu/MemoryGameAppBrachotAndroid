package com.example.finalprogeti;

import android.widget.ImageView;

import java.io.FileOutputStream;
import java.util.Random;

public class  Model {

    private int[] rArray ;
    /** Where is the 1st card which is chosen*/
     private int cardOne = 1;
    /**Where is the 2nd card which is chosen*/
     private int cardTwo = 1;
    /**Which card is pressed (1st or 2nd)*/
    private int pressNum = 1;
    /**How many pairs have  been found yet*/
    private int pairs = 0;
    /**The time of the player*/
    private String Time="";
    /**All the saved players information in one string*/
    private String NameAndTimeString;
    /**A Player who is the player which is playing at the moment*/
    private Player player;  // ?
    /**Saved file*/
    private FileOutputStream out;
    /**Name of the player who is saved*/
    private String FileName;    //?
    /**Time of the player who is saved*/
    private String FileTime;
    /**Random variable to shuffle the pictures in the deck*/
    private Random rand = new Random();
    /**Array which contains all the images (parallel to the deck itself)*/
    private ImageView[] deckArr ;
    /** 1st Player variable */
    private PlayerTwo firstPlayer;   //?
    /** 2nd player variable */
    private PlayerTwo secondPlayer;   //?
    /** 1st player name flag */


    public PlayerTwo getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(PlayerTwo firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public PlayerTwo getSecondPlayer() {
        return secondPlayer;
    }  //?

    public void setSecondPlayer(PlayerTwo secondPlayer) {
        this.secondPlayer = secondPlayer;
    }//?



    public Model() {
        this.rArray = new int[17];
        this.deckArr= new  ImageView[17];
        this.firstPlayer=new PlayerTwo("");
        this.secondPlayer=new PlayerTwo("");

    }
  public void intiTwoPlayer(PlayerTwo p)
  {
      p=new PlayerTwo(""); // Reset the 1st player name
  }



    public ImageView[] getDeckArr() {
        return deckArr;
    }

    public void setDeckArr(ImageView[] deckArr) {
        this.deckArr = deckArr;
    }





    public int[] getrArray() {
        return rArray;
    }

    public void setrArray(int[] rArray) {
        this.rArray = rArray;
    }

    public int getCardOne() {
        return cardOne;
    }// קלף ראשון

    public void setCardOne(int cardOne) {
        this.cardOne = cardOne;
    }

    public int getCardTwo() {
        return cardTwo;
    } // קלף שני

    public void setCardTwo(int cardTwo) {
        this.cardTwo = cardTwo;
    }

    public int getPressNum() {
        return pressNum;
    }

    public void setPressNum(int pressNum) {
        this.pressNum = pressNum;
    }

    public int getPairs() {
        return pairs;
    }

    public void setPairs(int pairs) {
        this.pairs = pairs;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNameAndTimeString() {
        return NameAndTimeString;
    }

    public void setNameAndTimeString(String nameAndTimeString) {
        NameAndTimeString = nameAndTimeString;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public FileOutputStream getOut() {
        return out;
    }

    public void setOut(FileOutputStream out) {
        this.out = out;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileTime() {
        return FileTime;
    }

    public void setFileTime(String fileTime) {
        FileTime = fileTime;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }
    public void initPlayer()
    {
      this.player=  new Player("No name yet", "");  // Create a new player with no information yet
    }
}
