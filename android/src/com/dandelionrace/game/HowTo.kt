package com.dandelionrace.game

import android.content.ClipData
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import com.badlogic.gdx.graphics.Texture

class HowTo : AppCompatActivity() {

    lateinit var bluemushroomPic: ImageView
    lateinit var greenmushroomPic: ImageView
    lateinit var leavesPic: ImageView
    lateinit var ghostPic: ImageView
    lateinit var switchPic: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_howto)

        bluemushroomPic = ImageView(this)
        greenmushroomPic = ImageView(this)
        leavesPic = ImageView(this)
        ghostPic = ImageView(this)
        switchPic = ImageView(this)

        bluemushroomPic.setImageResource(R.drawable.blue_mushroom)
        greenmushroomPic.setImageResource(R.drawable.green_mushroom)
        leavesPic.setImageResource(R.drawable.leaves)
        ghostPic.setImageResource(R.drawable.ghost)
        switchPic.setImageResource(R.drawable.switchpic)

        var mListView: ListView = findViewById(R.id.itemlistview)

        var bluemushroom: ItemInstructions = ItemInstructions("", "", bluemushroomPic)
        var greenmushroom: ItemInstructions = ItemInstructions("", "", greenmushroomPic)
        var leaves: ItemInstructions = ItemInstructions("", "", leavesPic)
        var ghost: ItemInstructions = ItemInstructions("", "", ghostPic)
        var switch: ItemInstructions = ItemInstructions("", "", switchPic)

        var itemList: ArrayList<ItemInstructions> = ArrayList()

        itemList.add(bluemushroom)
        itemList.add(greenmushroom)
        itemList.add(leaves)
        itemList.add(ghost)
        itemList.add(switch)

        var adapterItems: ItemListAdapter = ItemListAdapter(this, R.layout.custom_listview, itemList)
        //TODO: SET ADAPTER
        mListView.adapter

    }
}
