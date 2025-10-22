package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private final List<Todo> todos = new ArrayList<>();
private final AtomicInteger idCounter = new AtomicInteger();


    // GET /todos - mendapatkan daftar todo
    @GetMapping
    public List<Todo> getAllTodos() {
        return todos;
    }

    // POST /todos - menambahkan todo baru
    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        todo.setId(idCounter.incrementAndGet());
        todos.add(todo);
        return todo;
    }

    // PUT /todos/{id} - update todo berdasarkan id
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody Todo updatedTodo) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                todo.setTask(updatedTodo.getTask());
                todo.setCompleted(updatedTodo.isCompleted());
                return todo;
            }
        }
        return null; // bisa diganti dengan exception handling agar lebih rapi
    }

    // DELETE /todos/{id} - menghapus todo berdasarkan id
    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable int id) {
        todos.removeIf(todo -> todo.getId() == id);
        return "Todo with id " + id + " deleted.";
    }
}
