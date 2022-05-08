package com.erykhf.android.ohmebreakingbadtechtest.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.squareup.picasso.Picasso


object Util {

    fun getProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

    fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
        Picasso.get().load(uri).placeholder(progressDrawable).into(this)
    }

}
