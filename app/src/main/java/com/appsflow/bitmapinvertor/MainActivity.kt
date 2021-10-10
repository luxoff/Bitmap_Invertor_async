package com.appsflow.bitmapinvertor

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.DialogCompat
import androidx.core.graphics.drawable.toBitmap
import com.appsflow.bitmapinvertor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val progressDialog = Dialog(this@MainActivity)
        progressDialog.setContentView(R.layout.progress_bar)

        binding.apply {
            btnInvertAsyncTask.setOnClickListener {
                val bitmap = ivImage.drawable.toBitmap()

                val task = InvertBitmapAsyncTask(progressDialog)
                val invertedBitmap = task.execute(bitmap).get()

                ivImage.setImageBitmap(invertedBitmap)
            }

            btnInvertThread.setOnClickListener {
                progressDialog.show()
                val bitmap = ivImage.drawable.toBitmap()
                val task = MyRunnable(bitmap)
                val thread = Thread(task)
                thread.start()
                thread.join()
                val invertedBitmap = task.getBitmap()
                ivImage.setImageBitmap(invertedBitmap)
                progressDialog.dismiss()
            }
        }

    }

    class MyRunnable(private var bitmap: Bitmap) : Runnable {

        override fun run() {
            bitmap = bitmap.invertColors()
        }

        fun getBitmap(): Bitmap {
            return bitmap
        }
        // extension function to invert bitmap colors
        private fun Bitmap.invertColors(): Bitmap {
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
}



