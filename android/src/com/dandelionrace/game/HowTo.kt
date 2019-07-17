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

        val mListView: ListView = findViewById(R.id.itemlistview);

        var bluemushroom: ItemInstructions = ItemInstructions("Blue Mushroom", "Not only do they taste great, they also make you fly faster.", bluemushroomPic)
        var greenmushroom: ItemInstructions = ItemInstructions("Green Mushroom", "Ew, they taste bad and slow you down.", greenmushroomPic)
        var leaves: ItemInstructions = ItemInstructions("Rain of Leaves", "You drop leaves on your opponent and restrict his view.", leavesPic)
        var ghost: ItemInstructions = ItemInstructions("Ghost Mode", "You become a ghost beetle and can fly through all obstacles.", ghostPic)
        var switch: ItemInstructions = ItemInstructions("Switch", "Oops... this item lets you swap your flying height with that of your opponent.", switchPic)

        var itemList: ArrayList<ItemInstructions> = ArrayList()

        itemList.add(bluemushroom)
        itemList.add(greenmushroom)
        itemList.add(leaves)
        itemList.add(ghost)
        itemList.add(switch)

        var adapterItems: ItemListAdapter = ItemListAdapter(this, R.layout.custom_listview, itemList)
        //TODO: SET ADAPTER
        mListView.adapter = adapterItems

    }
}
