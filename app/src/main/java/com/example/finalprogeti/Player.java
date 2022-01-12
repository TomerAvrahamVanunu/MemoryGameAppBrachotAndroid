package com.example.finalprogeti;

public class Player {
    /** Player's name */
    private String name;
    /** Player's time */
    private String time;

    /** Return the player name */
    public String getName()
    {
        return name;
    }

    /** Change the player name */
    public void setName(String name)
    {
        this.name = name;
    }

    /** Return the player time */
    public String getTime()
    {
        return time;
    }

    /** Change the player time */
    public void setTime(String time)
    {
        this.time = time;
    }

    /** Constructor method */
    public Player(String name, String time)
    {
        this.name = name;
        this.time = time;
    }

}
