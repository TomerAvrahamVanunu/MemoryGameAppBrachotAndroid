package com.example.finalprogeti;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class PlayersScoreAdapter extends ArrayAdapter<Player> {

    /** Context of the adapter */
    private Context context;
    /** List of the saved players */
    private List<Player> players;

    public PlayersScoreAdapter(Context context, int resource, int textViewResourceId, List<Player> players) {
        super(context, resource, textViewResourceId, players);

        this.context=context;
        this.players=players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_players_score,parent,false);

        TextView tvNameAndTime = view.findViewById(R.id.tvPlayer); // The name and score of the saved player is put in a text-view
        Player temp = players.get(position); // Temporary player variable from the players list
        tvNameAndTime.setText(String.valueOf(temp.getName())); // Set the text-view in the specific place to the temporary's player name and score
        return view;
    }
}
