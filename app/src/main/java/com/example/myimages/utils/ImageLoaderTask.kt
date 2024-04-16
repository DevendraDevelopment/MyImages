package com.example.myimages.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.util.LruCache
import android.widget.ImageView
import java.net.URL

@Suppress("DEPRECATION")
class ImageLoaderTask(context: Context, private val imageView: ImageView) :
    AsyncTask<String, Void, Bitmap?>() {

    private val diskCache = DiskCache(context)
    private val memoryCache = LruCache<String, Bitmap>(maxMemorySize())

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): Bitmap? {
        val url = params[0]
        
        memoryCache.get(url)?.let {
            return it
        }

        diskCache.getBitmapFromCache(url ?: "")?.let {
            memoryCache.put(url ?: "", it)
            return it
        }

        try {
            val inputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val resizedBitmap = resizeBitmap(bitmap, imageView.width, imageView.height)

            memoryCache.put(url ?: "", resizedBitmap)
            diskCache.putBitmapInCache(url ?: "", resizedBitmap)

            return resizedBitmap
        } catch (e: Exception) {
            Log.e("ImageLoaderTask", "Error loading image: ${e.message}")
        }
        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }

    private fun maxMemorySize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }

    private fun resizeBitmap(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    }
}
