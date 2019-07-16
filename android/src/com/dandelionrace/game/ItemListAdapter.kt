package com.dandelionrace.game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

class ItemListAdapter(private val mContext: Context, internal var mResource: Int, objects: ArrayList<ItemInstructions>) : ArrayAdapter<ItemInstructions>(mContext, mResource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val name = getItem(position)!!.name
        val explanation = getItem(position)!!.explanation
        val picture = getItem(position)!!.picture

        val itemInstructions = ItemInstructions(name, explanation, picture)

        val inflater = LayoutInflater.from(mContext)
        convertView = inflater.inflate(mResource, parent, false)

        val tvName = convertView!!.findViewById<View>(R.id.itemName) as TextView
        val tvExplanation = convertView.findViewById<View>(R.id.itemExplanation) as TextView
        val tvImage = convertView.findViewById<View>(R.id.itemImage) as ImageView

        tvName.text = name
        tvExplanation.text = explanation
        tvImage.setImageDrawable(picture.drawable)

        return convertView
    }
}
