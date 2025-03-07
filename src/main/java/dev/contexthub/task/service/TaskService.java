package dev.contexthub.task.service;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.model.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    /**
     * Creates a new task with the provided details
     * 
     * @param request The task creation request
     * @return The created task
     */
    Task createTask(TaskRequest request);
    
    /**
     * Retrieves all open (not completed) tasks
     * 
     * @return List of open tasks
     */
    List<Task> getOpenTasks();
    
    /**
     * Retrieves a task by its ID
     * 
     * @param id The task ID
     * @return The task if found
     * @throws TaskNotFoundException if no task exists with the given ID
     */
    Task getTaskById(UUID id);
    
    /**
     * Updates an existing task with new details
     * 
     * @param id The ID of the task to update
     * @param request The updated task details
     * @return The updated task
     * @throws TaskNotFoundException if no task exists with the given ID
     */
    Task updateTask(UUID id, TaskRequest request);
} 