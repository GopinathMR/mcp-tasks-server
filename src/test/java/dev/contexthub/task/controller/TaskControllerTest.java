package dev.contexthub.task.controller;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.model.Task;
import dev.contexthub.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskRequest sampleRequest;
    private Task sampleTask;
    private UUID sampleId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        sampleId = UUID.randomUUID();
        
        sampleRequest = TaskRequest.builder()
                .title("Test Task")
                .description("Test Description")
                .startTime(now.plusHours(1))
                .aiPrompt("Test AI Prompt")
                .build();
        
        sampleTask = Task.builder()
                .id(sampleId)
                .title(sampleRequest.getTitle())
                .description(sampleRequest.getDescription())
                .startTime(sampleRequest.getStartTime())
                .aiPrompt(sampleRequest.getAiPrompt())
                .completed(false)
                .build();
    }

    @Test
    @DisplayName("Should create a task and return 201 Created")
    void createTask_ShouldReturnCreatedTask() {
        // Given
        when(taskService.createTask(any(TaskRequest.class))).thenReturn(sampleTask);

        // When
        ResponseEntity<Task> response = taskController.createTask(sampleRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleTask.getId(), response.getBody().getId());
        assertEquals(sampleTask.getTitle(), response.getBody().getTitle());
        assertEquals(sampleTask.getDescription(), response.getBody().getDescription());
        assertEquals(sampleTask.getStartTime(), response.getBody().getStartTime());
        assertEquals(sampleTask.getAiPrompt(), response.getBody().getAiPrompt());
    }

    @Test
    @DisplayName("Should return list of open tasks")
    void getOpenTasks_ShouldReturnListOfTasks() {
        // Given
        List<Task> tasks = Arrays.asList(sampleTask);
        when(taskService.getOpenTasks()).thenReturn(tasks);

        // When
        ResponseEntity<List<Task>> response = taskController.getOpenTasks();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(sampleTask.getId(), response.getBody().get(0).getId());
    }

    @Test
    @DisplayName("Should return task by ID")
    void getTaskById_ShouldReturnTask() {
        // Given
        when(taskService.getTaskById(sampleId)).thenReturn(sampleTask);

        // When
        ResponseEntity<Task> response = taskController.getTaskById(sampleId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleTask.getId(), response.getBody().getId());
    }

    @Test
    @DisplayName("Should update task and return updated task")
    void updateTask_ShouldReturnUpdatedTask() {
        // Given
        Task updatedTask = Task.builder()
                .id(sampleId)
                .title("Updated Title")
                .description("Updated Description")
                .startTime(now.plusDays(1))
                .aiPrompt("Updated AI Prompt")
                .completed(false)
                .build();
        
        when(taskService.updateTask(eq(sampleId), any(TaskRequest.class))).thenReturn(updatedTask);

        // When
        ResponseEntity<Task> response = taskController.updateTask(sampleId, sampleRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedTask.getId(), response.getBody().getId());
        assertEquals(updatedTask.getTitle(), response.getBody().getTitle());
        assertEquals(updatedTask.getDescription(), response.getBody().getDescription());
        assertEquals(updatedTask.getStartTime(), response.getBody().getStartTime());
        assertEquals(updatedTask.getAiPrompt(), response.getBody().getAiPrompt());
    }
} 