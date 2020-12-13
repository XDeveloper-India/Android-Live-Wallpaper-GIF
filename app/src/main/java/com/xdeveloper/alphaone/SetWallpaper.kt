package com.xdeveloper.alphaone

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SetWallpaper : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_wallpaper)
    }

    fun apply(view: View?) {
        val application = application as MainApplication
        application.gif = (findViewById<View>(R.id.gif) as EditText).text.toString()
        application.posX = (findViewById<View>(R.id.positionX) as EditText).text.toString().toFloat()
        application.posY = (findViewById<View>(R.id.positionY) as EditText).text.toString().toFloat()
        application.scaleX = (findViewById<View>(R.id.scaleX) as EditText).text.toString().toFloat()
        application.scaleY = (findViewById<View>(R.id.scaleY) as EditText).text.toString().toFloat()
        val intent = Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(this, GIFWallpaperService::class.java))
        startActivity(intent)
    }
}