package com.example.todolist.store

import androidx.arch.core.util.Function
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.models.*

class TodoStore : Store<TodoModel>, ViewModel() {
    private val state: MutableLiveData<TodoModel> = MutableLiveData()
    private val initState = TodoModel(listOf(), Visibility.All())

    override fun dispatch(action: Action) {
        state.value = reduce(state.value, action)
    }

    override fun subscribe(renderer: Renderer<TodoModel>, func: Function<TodoModel, TodoModel>) {
        renderer.render(Transformations.map(state, func))
    }

    private fun reduce(state: TodoModel?, action: Action): TodoModel {
        val newState = state ?: initState

        return when (action) {
            is AddTodo -> newState.copy(
                todos = newState.todos.toMutableList().apply {
                    add(Todo(action.text, action.id))
                }
            )
            is ToggleTodo -> newState.copy(
                todos = newState.todos.map {
                    if(it.id == action.id)
                        it.copy(status = !it.status)
                    else
                        it
                } as MutableList<Todo>
            )
            is SetVisibility -> newState.copy(
                visibility = action.visibility
            )
            is RemoveTodo -> newState.copy(
                todos = newState.todos.filter {
                    it.id != action.id
                } as MutableList<Todo>
            )
        }
    }
}