package com.example.myimages.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

class DiskCache(context: Context) {
    private val cacheDir: File = File(context.cacheDir, "images")

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    private fun getCacheFile(url: String): File {
        val fileName = url.hashCode().toString()
        return File(cacheDir, fileName)
    }

    fun getBitmapFromCache(url: String): Bitmap? {
        val cacheFile = getCacheFile(url)
        return if (cacheFile.exists()) {
            BitmapFactory.decodeFile(cacheFile.absolutePath)
        } else {
            null
        }
    }

    fun putBitmapInCache(url: String, bitmap: Bitmap) {
        val cacheFile = getCacheFile(url)
        try {
            val outputStream = FileOutputStream(cacheFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
