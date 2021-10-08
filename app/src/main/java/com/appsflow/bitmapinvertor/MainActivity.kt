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

    }

}