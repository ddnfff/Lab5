package com.example.lab3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
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

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        adapter = TaskAdapter(
            tasks = emptyList(),
            onDeleteClick = { position -> viewModel.deleteTask(position) },
            onToggleClick = { position -> viewModel.toggleTaskStatus(position) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.filteredTasks.observe(this) { taskList ->
            adapter.updateList(taskList)
        }

        findViewById<Button>(R.id.btnAddTask).setOnClickListener {
            val taskText = "Задача ${Random.nextInt(100)}"
            viewModel.addTask(taskText)
        }

        val spinner = findViewById<Spinner>(R.id.spinnerFilter)
        val spinnerItems = listOf("Все", "Выполненные", "Активные")

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerItems
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.setFilter(TaskFilter.ALL)
                    1 -> viewModel.setFilter(TaskFilter.DONE)
                    2 -> viewModel.setFilter(TaskFilter.ACTIVE)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}