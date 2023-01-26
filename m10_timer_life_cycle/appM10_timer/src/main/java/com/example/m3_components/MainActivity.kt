package com.example.m3_components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import kotlinx.coroutines.*

private const val CURRENT_AMOUNT_INT = "keyInt"
private const val MAX_CIRCLE_VALUE = "KeyMax"

class MainActivity : AppCompatActivity() {

    private val ioJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + ioJob)

    private var currentNumber: Int = 0
    private var maxCircle: Int = 0

    override fun onCreate(savedInstanceState: Bundle?): Unit = runBlocking {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.let {
            currentNumber = it.getInt(CURRENT_AMOUNT_INT)
            maxCircle = it.getInt(MAX_CIRCLE_VALUE)
        }

        val timer = findViewById<TextView>(R.id.timerText)
        val circle = findViewById<ProgressBar>(R.id.progressBar1)
        val seekBar = findViewById<SeekBar>(R.id.seekProgressBar)
        val button = findViewById<Button>(R.id.buttonStart)

        suspend fun timerTik() {
            button.isEnabled = false
            seekBar.isEnabled = false
            while (currentNumber > 0) {
                timer.text = currentNumber.toString()
                circle.progress = currentNumber
                delay(1000L)
                currentNumber--
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
        if (currentNumber == 0) {
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    this@MainActivity.currentNumber = p1
                    timer.text = p1.toString()
                    circle.max = p1
                    maxCircle = p1
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {
                    Toast.makeText(this@MainActivity, "Запустим таймер?", LENGTH_SHORT).show()
                }
            })
        }

        button.setOnClickListener {
            scope.launch {
                timerTik()
            }
        }
        if (currentNumber > 0) {
            circle.max = maxCircle
            Log.d("d", "?????????????????????___ $maxCircle ___???????????????")
            scope.launch { timerTik() }}
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_AMOUNT_INT, currentNumber)
        outState.putInt(MAX_CIRCLE_VALUE, maxCircle)
    }

    override fun onDestroy() {
        super.onDestroy()
        ioJob.cancel()
    }
}
