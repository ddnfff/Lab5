package com.example.lab3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Инициализация ViewModel для задач
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // 2. Настройка RecyclerView
        adapter = TaskAdapter(emptyList()) { position ->
            viewModel.deleteTask(position)   // обработчик удаления
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 3. Подписка на LiveData с задачами
        viewModel.tasks.observe(this) { taskList ->
            adapter.updateList(taskList)
        }

        // 4. Обработчик добавления задачи
        findViewById<Button>(R.id.btnAddUser).setOnClickListener {
            val taskText = "Задача ${Random.nextInt(100)}"   // или диалог ввода
            viewModel.addTask(taskText)
        }
    }
}







