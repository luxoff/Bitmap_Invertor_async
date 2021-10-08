package com.appsflow.bitmapinvertor

import android.app.Dialog
import android.graphics.*
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import com.appsflow.bitmapinvertor.databinding.ProgressBarBinding
import java.lang.Exception

public class InvertBitmapAsyncTask(progressBar: Dialog) : AsyncTask<Bitmap, Void, Bitmap>() {
    val progressBarDialog = progressBar
    override fun onPreExecute() {
        super.onPreExecute()

        progressBarDialog.show()
    }

    override fun doInBackground(vararg params: Bitmap): Bitmap {
        val bitmap :Bitmap = params[0]
        return bitmap.invertColors()
    }

    override fun onPostExecute(result: Bitmap) {
        super.onPostExecute(result)

        progressBarDialog.dismiss()
    }

    // extension function to invert bitmap colors
    fun Bitmap.invertColors(): Bitmap {
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )

        val matrixInvert = ColorMatrix().apply {
            set(
                floatArrayOf(
                    -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
                    0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
                    0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
                    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                )
            )
        }

        val paint = Paint()
        ColorMatrixColorFilter(matrixInvert).apply {
            paint.colorFilter = this
        }

        Canvas(bitmap).drawBitmap(this, 0f, 0f, paint)
        return bitmap
    }

}