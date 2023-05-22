package com.pride.test.flow.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converter {
    @TypeConverter
    fun convertToByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap == null) {
            null
        } else {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 4, outputStream)
            outputStream.toByteArray()
        }
    }

    @TypeConverter
    fun convertToBitmap(byteArr: ByteArray?): Bitmap? {
        return if (byteArr == null || byteArr.isEmpty()) {
            null
        } else {
            return BitmapFactory.decodeByteArray(byteArr, 0, byteArr.size)
        }
    }
}