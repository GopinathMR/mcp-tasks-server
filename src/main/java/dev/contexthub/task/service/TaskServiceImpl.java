package dev.contexthub.task.service;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.exception.TaskNotFoundException;
import dev.contexthub.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    
    // In-memory storage for tasks
    private final Map<UUID, Task> taskRepository = new ConcurrentHashMap<>();
    
    @Override
    public Task createTask(TaskRequest request) {
        Task task = Task.createNew(
            request.getTitle(),
            request.getDescription(),
            request.getStartTime(),
            request.getAiPrompt()
        );
        
        taskRepository.put(task.getId(), task);
        return task;
    }
    
    @Override
    public List<Task> getOpenTasks() {
        return taskRepository.values().stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }
    
    @Override
    public Task getTaskById(UUID id) {
        Task task = taskRepository.get(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        return task;
    }
    
    @Override
    public Task updateTask(UUID id, TaskRequest request) {
        Task existingTask = getTaskById(id);
        
        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStartTime(request.getStartTime());
        existingTask.setAiPrompt(request.getAiPrompt());
        
        taskRepository.put(id, existingTask);
        return existingTask;
    }
} 