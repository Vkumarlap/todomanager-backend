package com.example.todomanager.controller;

import com.example.todomanager.dto.TaskRequest;
import com.example.todomanager.model.Task;
import com.example.todomanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {
    "https://kumar-todo-manager.vercel.app",
    "http://localhost:5173",
    "http://127.0.0.1:5173",
    "http://localhost:3000"
})
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getTasks(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String priority,
        @RequestParam(required = false) String search
    ) {
        return service.findAll(status, priority, search);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.updateStatus(id, body == null ? null : body.get("status"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        service.delete(id);
    }
}
