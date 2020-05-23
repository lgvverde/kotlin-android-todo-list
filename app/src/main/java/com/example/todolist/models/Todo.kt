package com.example.todolist.models

data class Todo(val text: String, val id: Long, val status: Boolean = false)