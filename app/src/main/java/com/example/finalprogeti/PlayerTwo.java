package com.example.finalprogeti;

public class PlayerTwo {
    /** Player's name */
    private String name;
    /** Number of pairs the player has */
    private int pairs;

    /** Constructor method */
    public PlayerTwo(String name)
    {
        this.name = name;
        this.pairs=0;
    }

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

    /** Return the player pairs */
    public int getPairs()
    {
        return pairs;
    }

    public void setPairs(int pairs) {
        this.pairs = pairs;
    }

    /** Increase the player's points by one */
    public void oneMorePoint()
    {
        this.pairs++;
    }
}
