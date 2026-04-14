package com.example.lab3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _allTasks = MutableLiveData<List<Task>>()
    private val _visibleTasks = MutableLiveData<List<Task>>()
    val visibleTasks: LiveData<List<Task>> = _visibleTasks

    private var currentFilter = TaskFilter.ALL
    private var currentSort = SortType.BY_DATE

    init {
        _allTasks.value = listOf(
            Task("Купить", false, System.currentTimeMillis(), 2),
            Task("Хранить", true, System.currentTimeMillis() - 10000, 3),
            Task("Продать", false, System.currentTimeMillis() - 20000, 1)
        )
        updateTasks()
    }

    fun addTask(title: String, priority: Int = 1) {
        val updated = _allTasks.value.orEmpty() + Task(
            title = title,
            isDone = false,
            createdAt = System.currentTimeMillis(),
            priority = priority
        )
        _allTasks.value = updated
        updateTasks()
    }

    fun deleteTask(position: Int) {
        val currentList = _visibleTasks.value.orEmpty()
        if (position !in currentList.indices) return

        val taskToDelete = currentList[position]
        val updated = _allTasks.value.orEmpty().toMutableList().apply {
            remove(taskToDelete)
        }

        _allTasks.value = updated
        updateTasks()
    }

    fun toggleTaskStatus(position: Int) {
        val currentList = _visibleTasks.value.orEmpty()
        if (position !in currentList.indices) return

        val selectedTask = currentList[position]

        val updated = _allTasks.value.orEmpty().map { task ->
            if (task == selectedTask) task.copy(isDone = !task.isDone) else task
        }

        _allTasks.value = updated
        updateTasks()
    }

    fun setFilter(filter: TaskFilter) {
        currentFilter = filter
        updateTasks()
    }

    fun setSort(sortType: SortType) {
        currentSort = sortType
        updateTasks()
    }

    private fun updateTasks() {
        val filtered = when (currentFilter) {
            TaskFilter.ALL -> _allTasks.value.orEmpty()
            TaskFilter.DONE -> _allTasks.value.orEmpty().filter { it.isDone }
            TaskFilter.ACTIVE -> _allTasks.value.orEmpty().filter { !it.isDone }
        }

        val sorted = when (currentSort) {
            SortType.BY_NAME -> filtered.sortedBy { it.title.lowercase() }
            SortType.BY_DATE -> filtered.sortedByDescending { it.createdAt }
            SortType.BY_PRIORITY -> filtered.sortedBy { it.priority }
        }

        _visibleTasks.value = sorted
    }
}