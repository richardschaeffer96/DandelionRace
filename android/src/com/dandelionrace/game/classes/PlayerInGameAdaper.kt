package com.dandelionrace.game.classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.badlogic.gdx.Game
import com.dandelionrace.game.R

class PlayerInGameAdapter (val context: Context, private val dataSource: ArrayList<PlayerOnServer>) : BaseAdapter() {
    //1
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.players_in_game_list_item, parent, false)
        val playername = rowView.findViewById(R.id.playerInGameName) as TextView
        val playerready = rowView.findViewById(R.id.playerInGameReady) as TextView
        val player = getItem(position) as PlayerOnServer

        playername.text = player.name
        if (player.ready) {
            playerready.text = "Spieler bereit"
        } else {
            playerready.text = "Spieler nicht bereit"
        }


        return rowView
    }

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}