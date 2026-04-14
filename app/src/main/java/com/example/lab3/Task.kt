package com.example.lab3

data class Task(
    val title: String,
    val isDone: Boolean = false
)

enum class TaskFilter {
    ALL, DONE, ACTIVE
}