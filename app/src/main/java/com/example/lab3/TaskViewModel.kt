package com.example.lab3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<String>>()
    val tasks: LiveData<List<String>> = _tasks

    init {
        _tasks.value = listOf("Купить молоко", "Почистить зубы", "Сделать домашку")
    }

    fun addTask(text: String) {
        _tasks.value = _tasks.value?.plus(text) ?: listOf(text)
    }

    fun deleteTask(position: Int) {
        _tasks.value = _tasks.value?.toMutableList()?.apply {
            removeAt(position)
        }
    }
}