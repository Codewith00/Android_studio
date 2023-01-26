package com.example.m1_hello_world

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.m1_hello_world.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var amountCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.leftButton.isEnabled = false
        binding.rightButton.setOnClickListener {
            amountCount++
            binding.counterNumbers.text = amountCount.toString()
            if (amountCount == 50) {
                binding.resetButton.visibility = View.VISIBLE
                binding.centerText.setTextColor(Color.RED)
                binding.centerText.text = "Свободных мест не осталось!"
                binding.rightButton.isEnabled = false


            }
            if (amountCount > 0) {
                binding.leftButton.isEnabled = true
                binding.centerText.setTextColor(Color.BLUE)
                binding.centerText.text = "Колиство свободных мест - ${50 - amountCount}"
            }
        }
        binding.leftButton.setOnClickListener {
            amountCount--
            binding.counterNumbers.text = amountCount.toString()
            if (amountCount > 0) {
                binding.centerText.text = "Колиство свободных мест - ${50 - amountCount}"
                binding.rightButton.isEnabled = true
            } else {
                binding.leftButton.isEnabled = false
                binding.centerText.setTextColor(Color.GREEN)
                binding.centerText.text = "Все места свободны"
            }

        }
        binding.resetButton.setOnClickListener {
            amountCount = 0
            binding.counterNumbers.text = amountCount.toString()
            binding.centerText.text = "Все места свободны"
            binding.centerText.setTextColor(Color.GREEN)
            binding.leftButton.isEnabled = false
            binding.rightButton.isEnabled = true
            binding.resetButton.visibility = View.INVISIBLE
        }
    }
}