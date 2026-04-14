package com.example.lab3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _allTasks = MutableLiveData<List<Task>>()
    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> = _filteredTasks

    private var currentFilter = TaskFilter.ALL

    init {
        _allTasks.value = listOf(
            Task("Купить молоко", false),
            Task("Сделать лабораторную", true),
            Task("Позвонить другу", false)
        )
        updateFilteredTasks()
    }

    fun addTask(title: String) {
        val updated = _allTasks.value.orEmpty() + Task(title, false)
        _allTasks.value = updated
        updateFilteredTasks()
    }

    fun deleteTask(position: Int) {
        val currentList = _filteredTasks.value.orEmpty()
        if (position !in currentList.indices) return

        val taskToDelete = currentList[position]
        val updated = _allTasks.value.orEmpty().toMutableList().apply {
            remove(taskToDelete)
        }

        _allTasks.value = updated
        updateFilteredTasks()
    }

    fun toggleTaskStatus(position: Int) {
        val currentList = _filteredTasks.value.orEmpty()
        if (position !in currentList.indices) return

        val selectedTask = currentList[position]

        val updated = _allTasks.value.orEmpty().map { task ->
            if (task == selectedTask) {
                task.copy(isDone = !task.isDone)
            } else {
                task
            }
        }

        _allTasks.value = updated
        updateFilteredTasks()
    }

    fun setFilter(filter: TaskFilter) {
        currentFilter = filter
        updateFilteredTasks()
    }

    private fun updateFilteredTasks() {
        val source = _allTasks.value.orEmpty()

        _filteredTasks.value = when (currentFilter) {
            TaskFilter.ALL -> source
            TaskFilter.DONE -> source.filter { it.isDone }
            TaskFilter.ACTIVE -> source.filter { !it.isDone }
        }
    }
}