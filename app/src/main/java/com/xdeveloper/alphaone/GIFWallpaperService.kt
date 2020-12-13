package com.xdeveloper.alphaone

import android.service.wallpaper.WallpaperService
import android.graphics.Movie
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Handler
import android.util.Log
import android.view.SurfaceHolder
import java.io.FileDescriptor
import java.io.IOException
import java.io.PrintWriter

class GIFWallpaperService : WallpaperService() {
    private var posX = 0f
    private var posY = 0f
    private var scaleX = 0f
    private var scaleY = 0f
    private var gif: String? = null

    override fun onCreate() {
        super.onCreate()
        val application = application as MainApplication
        gif = application.gif
        scaleX = application.scaleX
        scaleY = application.scaleY
        posX = application.posX
        posY = application.posY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun dump(fd: FileDescriptor, out: PrintWriter, args: Array<String>) {
        super.dump(fd, out, args)
    }

    override fun onCreateEngine(): Engine? {
        return try {
            val movie = Movie.decodeStream(
                    resources.assets.open(gif!!))
            GIFWallpaperEngine(movie)
        } catch (e: IOException) {
            Log.d("GIF", "Could not load asset")
            null
        }
    }

    internal inner class GIFWallpaperEngine(private val movie: Movie) : Engine() {
        private var holder: SurfaceHolder? = null
        private var visible = false
        private val handler: Handler = Handler()
        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            holder = surfaceHolder
        }

        private val drawGIF = Runnable { draw() }
        private fun draw() {
            if (visible) {
                val canvas = holder!!.lockCanvas()
                canvas.save()
                // Adjust size and position so that
                // the image looks good on your screen
                canvas.scale(scaleX, scaleY)
                movie.draw(canvas, posX, posY)
                canvas.restore()
                holder!!.unlockCanvasAndPost(canvas)
                movie.setTime((System.currentTimeMillis() % movie.duration()).toInt())
                handler.removeCallbacks(drawGIF)
                val frameDuration = 20
                handler.postDelayed(drawGIF, frameDuration.toLong())
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                handler.post(drawGIF)
            } else {
                handler.removeCallbacks(drawGIF)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacks(drawGIF)
        }

    }
}