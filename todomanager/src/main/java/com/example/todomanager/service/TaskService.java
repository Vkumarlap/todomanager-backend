package com.example.todomanager.service;

import com.example.todomanager.dto.TaskRequest;
import com.example.todomanager.model.Task;
import com.example.todomanager.model.TaskPriority;
import com.example.todomanager.model.TaskStatus;
import com.example.todomanager.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll(String status, String priority, String search) {
        return repository.findAll().stream()
            .filter(task -> status == null || status.isBlank()
                || task.getStatus().name().equalsIgnoreCase(status))
            .filter(task -> priority == null || priority.isBlank()
                || task.getPriority().name().equalsIgnoreCase(priority))
            .filter(task -> search == null || search.isBlank()
                || task.getTitle().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))
                || (task.getDescription() != null
                    && task.getDescription().toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))))
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
            .toList();
    }

    public Task findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
    }

    public Task create(TaskRequest request) {
        validate(request);
        Task task = new Task();
        copyFields(request, task);
        return repository.save(task);
    }

    public Task update(Long id, TaskRequest request) {
        validate(request);
        Task task = findById(id);
        copyFields(request, task);
        return repository.save(task);
    }

    public Task updateStatus(Long id, String statusValue) {
        if (statusValue == null || statusValue.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required.");
        }
        TaskStatus status;
        try {
            status = TaskStatus.valueOf(statusValue.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be PENDING or COMPLETED.");
        }
        Task task = findById(id);
        task.setStatus(status);
        return repository.save(task);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    private void validate(TaskRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title is required.");
        }
        if (request.getTitle().trim().length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task title must be 150 characters or fewer.");
        }
        if (request.getPriority() != null && !List.of(TaskPriority.values()).contains(request.getPriority())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid task priority.");
        }
        if (request.getStatus() != null && !List.of(TaskStatus.values()).contains(request.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid task status.");
        }
    }

    private void copyFields(TaskRequest request, Task task) {
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority() == null ? TaskPriority.MEDIUM : request.getPriority());
        task.setStatus(request.getStatus() == null ? TaskStatus.PENDING : request.getStatus());
    }
}
