package com.example.m11_timer_data_storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val repository = Repository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClear = findViewById<Button>(R.id.btnRemove)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val editText = findViewById<EditText>(R.id.editText)
        val textView = findViewById<TextView>(R.id.textView)

        if (repository.getText() != null) textView.text = repository.getText()

        btnSave.setOnClickListener {
            repository.saveText(editText.text.toString())
        }
        btnClear.setOnClickListener {
            repository.clearText()
        }

    }
}