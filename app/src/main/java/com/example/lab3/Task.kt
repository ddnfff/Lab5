package com.example.lab3

data class Task(
    val title: String,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val priority: Int = 1
)

enum class TaskFilter {
    ALL, DONE, ACTIVE
}

enum class SortType {
    BY_NAME, BY_DATE, BY_PRIORITY
}