package dev.contexthub.task.service;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.exception.TaskNotFoundException;
import dev.contexthub.task.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceImplTest {

    private TaskService taskService;
    private TaskRequest sampleRequest;
    private LocalDateTime now;
    
    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl();
        now = LocalDateTime.now();
        
        sampleRequest = TaskRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .startTime(now.plusHours(1))
                .aiPrompt("Test AI Prompt")
                .build();
    }
    
    @Test
    @DisplayName("Should create a task successfully")
    void createTask_ShouldReturnCreatedTask() {
        // When
        Task createdTask = taskService.createTask(sampleRequest);
        
        // Then
        assertNotNull(createdTask);
        assertNotNull(createdTask.getId());
        assertEquals(sampleRequest.getTitle(), createdTask.getTitle());
        assertEquals(sampleRequest.getDescription(), createdTask.getDescription());
        assertEquals(sampleRequest.getStartTime(), createdTask.getStartTime());
        assertEquals(sampleRequest.getAiPrompt(), createdTask.getAiPrompt());
        assertFalse(createdTask.isCompleted());
    }
    
    @Test
    @DisplayName("Should return only open tasks")
    void getOpenTasks_ShouldReturnOnlyOpenTasks() {
        // Given
        Task task1 = taskService.createTask(sampleRequest);
        
        TaskRequest request2 = TaskRequest.builder()
                .title("Task 2")
                .description("Description 2")
                .startTime(now.plusHours(2))
                .aiPrompt("AI Prompt 2")
                .build();
        Task task2 = taskService.createTask(request2);
        
        // When
        List<Task> openTasks = taskService.getOpenTasks();
        
        // Then
        assertEquals(2, openTasks.size());
        
        // When - Mark task1 as completed
        task1.setCompleted(true);
        
        // Then
        openTasks = taskService.getOpenTasks();
        assertEquals(1, openTasks.size());
        assertEquals(task2.getId(), openTasks.get(0).getId());
    }
    
    @Test
    @DisplayName("Should return task by ID")
    void getTaskById_ShouldReturnCorrectTask() {
        // Given
        Task createdTask = taskService.createTask(sampleRequest);
        UUID taskId = createdTask.getId();
        
        // When
        Task retrievedTask = taskService.getTaskById(taskId);
        
        // Then
        assertEquals(createdTask.getId(), retrievedTask.getId());
        assertEquals(createdTask.getTitle(), retrievedTask.getTitle());
        assertEquals(createdTask.getDescription(), retrievedTask.getDescription());
        assertEquals(createdTask.getStartTime(), retrievedTask.getStartTime());
        assertEquals(createdTask.getAiPrompt(), retrievedTask.getAiPrompt());
    }
    
    @Test
    @DisplayName("Should throw exception when task not found")
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        
        // When & Then
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(nonExistentId);
        });
    }
    
    @Test
    @DisplayName("Should update task fields")
    void updateTask_ShouldUpdateTaskFields() {
        // Given
        Task createdTask = taskService.createTask(sampleRequest);
        UUID taskId = createdTask.getId();
        
        TaskRequest updateRequest = TaskRequest.builder()
                .title("Updated Title")
                .description("Updated Description")
                .startTime(now.plusDays(1))
                .aiPrompt("Updated AI Prompt")
                .build();
        
        // When
        Task updatedTask = taskService.updateTask(taskId, updateRequest);
        
        // Then
        assertEquals(taskId, updatedTask.getId());
        assertEquals(updateRequest.getTitle(), updatedTask.getTitle());
        assertEquals(updateRequest.getDescription(), updatedTask.getDescription());
        assertEquals(updateRequest.getStartTime(), updatedTask.getStartTime());
        assertEquals(updateRequest.getAiPrompt(), updatedTask.getAiPrompt());
    }
    
    @Test
    @DisplayName("Should throw exception when updating non-existent task")
    void updateTask_ShouldThrowException_WhenTaskNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        
        // When & Then
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(nonExistentId, sampleRequest);
        });
    }
} 