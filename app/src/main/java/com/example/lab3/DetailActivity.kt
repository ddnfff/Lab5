package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val taskText = intent.getStringExtra("TASK_TEXT") ?: "Без имени"

        // если в layout есть TextView, например:
        findViewById<TextView>(R.id.tvTaskDetail).text = taskText

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}