package com.example.todomanager.dto;

import com.example.todomanager.model.TaskPriority;
import com.example.todomanager.model.TaskStatus;
import java.time.LocalDate;

public class TaskRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskPriority priority;
    private TaskStatus status;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
