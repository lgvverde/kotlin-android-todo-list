package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.store.TodoStore
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.todolist.models.AddTodo
import com.example.todolist.models.Todo
import com.example.todolist.models.TodoModel
import com.example.todolist.models.Visibility
import com.example.todolist.store.Renderer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Renderer<TodoModel> {
    private lateinit var store: TodoStore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store = ViewModelProvider(this).get(TodoStore::class.java)
        store.subscribe(this, mapStateToProps)

        addButton.setOnClickListener { store.dispatch(AddTodo(etPersonName.text.toString())) }
        fab.setOnClickListener { openDialog() }
    }

    override fun render(model: LiveData<TodoModel>) {
        TODO("Not yet implemented")
    }

    private fun openDialog(){

    }

    private val mapStateToProps = Function<TodoModel, TodoModel> {
        val keep: (Todo) -> Boolean = when(it.visibility){
            is Visibility.All -> { _ -> true }
            is Visibility.Active -> { t: Todo -> !t.status }
            is Visibility.Completed -> { t: Todo -> t.status }
        }

        return@Function it.copy(todos = it.todos.filter{ keep(it) })
    }
}
