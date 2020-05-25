package com.example.todolist.store

import com.example.todolist.models.Action
import androidx.arch.core.util.Function

interface Store<T> {
    fun dispatch(action: Action)
    fun subscribe(renderer: Renderer<T>, func: Function<T, T> = Function { it }  )
}