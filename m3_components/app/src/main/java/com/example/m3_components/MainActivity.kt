package com.example.m3_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var currentNumber: Int = 0

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = findViewById<TextView>(R.id.timerText)
        val circle = findViewById<ProgressBar>(R.id.progressBar1)
        val seekBar = findViewById<SeekBar>(R.id.seekProgressBar)
        val button = findViewById<Button>(R.id.buttonStart)


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                this@MainActivity.currentNumber = p1
                timer.text = p1.toString()
                circle.max = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
                Toast.makeText(this@MainActivity, "Запустим таймер?", LENGTH_SHORT).show()
            }

        })
        suspend fun timerTik() {
            button.isEnabled = false
            seekBar.isEnabled = false
            while (currentNumber > 0) {
                currentNumber--
                timer.text = currentNumber.toString()
                circle.progress = currentNumber
                delay(1000L)
            }
            button.isEnabled = true
            seekBar.isEnabled = true
            seekBar.progress = currentNumber
            if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
        }


        button.setOnClickListener {
            scope.launch {
                timerTik()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


}
