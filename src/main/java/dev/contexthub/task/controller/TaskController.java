package dev.contexthub.task.controller;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.model.Task;
import dev.contexthub.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    
    private final TaskService taskService;
    
    /**
     * Creates a new task
     * 
     * @param request The task creation request
     * @return The created task with HTTP 201 Created status
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves all open (not completed) tasks
     * 
     * @return List of open tasks
     */
    @GetMapping
    public ResponseEntity<List<Task>> getOpenTasks() {
        List<Task> openTasks = taskService.getOpenTasks();
        return ResponseEntity.ok(openTasks);
    }
    
    /**
     * Retrieves a task by its ID
     * 
     * @param id The task ID
     * @return The task if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    
    /**
     * Updates an existing task
     * 
     * @param id The ID of the task to update
     * @param request The updated task details
     * @return The updated task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody TaskRequest request) {
        Task updatedTask = taskService.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }
} 