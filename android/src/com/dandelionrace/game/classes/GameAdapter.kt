package com.dandelionrace.game.classes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.badlogic.gdx.Game
import com.dandelionrace.game.R

class GameAdapter (val context: Context, private val dataSource: ArrayList<DandelionGame>) : BaseAdapter() {
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
        val rowView = inflater.inflate(R.layout.game_view_list_item, parent, false)
        val titleTextView = rowView.findViewById(R.id.gameviewlistname) as TextView
        val hostTextView = rowView.findViewById(R.id.gameviewlisthost) as TextView
        val game = getItem(position) as DandelionGame

        titleTextView.text = game.name + "  - Anzahl Spieler: " + game.numberOfPlayers
        hostTextView.text = "Host: " + game.host

        return rowView
    }

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}