package com.example.finalprogeti;

import android.os.SystemClock;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

public class Controler {

    Model model = new Model();
    private InputStream in;
    /**String with all the players names and scores*/
    private FileOutputStream out;
    /**Name of the player who is saved*/

    /**All the saved players information in one string*/
    private String NameAndTimeString;
    private String FileScore;
    private ArrayList<Player> playerList= new ArrayList<Player>();
    /** Adapter from thr list of the players to the list-view */
    /**Name of the player who is saved*/
    private String FileName;
    /**Time of the player who is saved*/
    private String FileTime;

    public OutputStream getOut() {
        return out;
    }

    public void setOut(FileOutputStream out) {
        this.out = out;
    }

    public String getNameAndTimeString() {
        return NameAndTimeString;
    }

    public void setNameAndTimeString(String nameAndTimeString) {
        NameAndTimeString = nameAndTimeString;
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

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public String getFileScore() {
        return FileScore;
    }

    public void setFileScore(String fileScore) {
        FileScore = fileScore;
    }

    /** List-view which shows all the saved information of the players */

    public Controler(InputStream in) {
         this.in=in;
    }
    public Controler(FileOutputStream out) {
        this.out=out;
    }
    public Controler() {

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
   public void inItRarray() // Put in the array couples of different pictures
    {
         model.getrArray()[1] = R.drawable.pic1;
         model.getrArray()[2] = R.drawable.pic2;
         model.getrArray()[3] = R.drawable.pic3;
         model.getrArray()[4] = R.drawable.pic4;
         model.getrArray()[5] = R.drawable.pic5;
         model.getrArray()[6] = R.drawable.pic6;
         model.getrArray()[7] = R.drawable.pic7;
         model.getrArray()[8] = R.drawable.pic8;
         model.getrArray()[9] = R.drawable.pic1;
         model.getrArray()[10] = R.drawable.pic2;
         model.getrArray()[11] = R.drawable.pic3;
         model.getrArray()[12] = R.drawable.pic4;
         model.getrArray()[13] = R.drawable.pic5;
         model.getrArray()[14] = R.drawable.pic6;
         model.getrArray()[15] = R.drawable.pic7;
         model.getrArray()[16] = R.drawable.pic8;
        shuffle( model.getrArray());
    }



    private void shuffle(int[] arr)
    {
        for (int i = 1; i < arr.length; i++) {
            int n = model.getRand().nextInt(8);
            n++;
            int a = arr[n];
            arr[n] = arr[i];
            arr[i] = a;
        }
    }
    public void addonePairs()
    {
         model.setPairs(model.getPairs()+1);
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

   public void DisableAllCards()
    {
        for (int i = 1; i < model.getDeckArr().length; i++) {
            model.getDeckArr()[i].setEnabled(false);
        }
    }

    /** Enable all the cards which still on the board */
    public void EnableCards()
    {
        for (int i = 1; i < model.getDeckArr().length; i++) {
            if (model.getDeckArr()[i].getVisibility() == View.VISIBLE) // If the card is still visible visible
                model.getDeckArr()[i].setEnabled(true); // Make it enable and ready to be pressed
        }
    }


    public void fileoutName() {
        try {
            FileName = model.getPlayer().getName(); // Put the saved name in a variable
            FileTime = model.getPlayer().getTime(); // Put the saved time in a variable
            // ~ - Separates the the players information
            NameAndTimeString = FileName + ": " + FileTime + " sec'~"; // add the player score to the string with all of the scores (~ separates the information of each player)
            // out = openFileOutput("Players_File", MODE_APPEND); // The name of the file (Mode Append because we want it to show all the previous players also)
            if (NameAndTimeString != null) {
                try {
                    out.write(NameAndTimeString.getBytes(), 0, NameAndTimeString.length()); // Add the new information to the file
                    out.close(); // close the file
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        } finally {

        }
    }

    public void OpenFile()
    {
        //  in = openFileInput("Players_File"); // Find the file with it's name
        byte[] bufferName = new byte[4096]; // Make a big array of bytes that will contain the file's information
        try {
            // Put the information in the buffer
            in.read(bufferName);
           FileScore = new String(bufferName);
            in.close();
            if (FileScore != null) // The file isn't empty
            {
                String nameInIndex = ""; // Variable of the name
                for (int i = 0; i < FileScore.length(); i++) {
                    // ~ - Separates the the players information
                    if (FileScore.charAt(i) == '~') // The characters in the place of i have combined the information of the player
                    {   model.initPlayer();
                       // Player savedPlayer = model.getPlayer();//   new Player("No name yet", "No time yet"); // Create a new "No information" player
                        model.getPlayer().setName(nameInIndex); // Give the player his information of the saved player in the place
                        playerList.add(model.getPlayer()); // Add the player to the list
                        nameInIndex = ""; // Reset the player's name variable
                    } else // The characters haven't combined a name yet
                        nameInIndex = nameInIndex + FileScore.charAt(i);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}