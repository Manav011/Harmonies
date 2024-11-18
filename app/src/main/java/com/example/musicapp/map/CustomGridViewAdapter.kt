package com.example.musicapp.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.musicapp.R


class CustomGridViewAdapter(val context: Context, private val ImageList: ArrayList<Int>, private val names: Array<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return ImageList.size
    }

    override fun getItem(position: Int): Any {
//        return ImageList.get(position)
        return ImageList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var myView = convertView
        val holder: ViewHolder

        if (myView == null) {
            val mInflater = (context as Activity).layoutInflater
            myView = mInflater.inflate(R.layout.grid_item, parent, false)
            holder = ViewHolder()

            holder.mImageView = myView!!.findViewById(R.id.gridImageView) as ImageView
            holder.mTextView = myView!!.findViewById(R.id.gridTextView) as TextView
            myView.tag = holder
        } else {
            holder = myView.tag as ViewHolder
        }

        holder.mImageView.setImageResource(ImageList[position])
        holder.mTextView.text = names[position]

        return myView
    }

    class ViewHolder {
        lateinit var mImageView: ImageView
        lateinit var mTextView: TextView
    }
}
