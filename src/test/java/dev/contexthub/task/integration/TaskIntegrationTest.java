package dev.contexthub.task.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("default")
class TaskIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private ObjectMapper objectMapper;
    private TaskRequest sampleRequest;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
        System.out.println("baseUrl: " + baseUrl);
        now = LocalDateTime.now();
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        sampleRequest = TaskRequest.builder()
                .title("Integration Test Task")
                .description("Integration Test Description")
                .startTime(now.plusHours(1))
                .aiPrompt("Integration Test AI Prompt")
                .build();
    }

    @Test
    @DisplayName("Should create and retrieve a task")
    void testCreateAndGetTask() {
        // Create a task
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskRequest> request = new HttpEntity<>(sampleRequest, headers);
        
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(baseUrl, request, Task.class);
        
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals(sampleRequest.getTitle(), createResponse.getBody().getTitle());
        assertEquals(sampleRequest.getDescription(), createResponse.getBody().getDescription());
        assertEquals(sampleRequest.getStartTime(), createResponse.getBody().getStartTime());
        assertEquals(sampleRequest.getAiPrompt(), createResponse.getBody().getAiPrompt());
        assertFalse(createResponse.getBody().isCompleted());
        
        // Get the created task by ID
        UUID taskId = createResponse.getBody().getId();
        ResponseEntity<Task> getResponse = restTemplate.getForEntity(baseUrl + "/" + taskId, Task.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(taskId, getResponse.getBody().getId());
        assertEquals(sampleRequest.getTitle(), getResponse.getBody().getTitle());
    }

    @Test
    @DisplayName("Should retrieve open tasks")
    void testGetOpenTasks() {
        // Create a task
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskRequest> request = new HttpEntity<>(sampleRequest, headers);
        
        restTemplate.postForEntity(baseUrl, request, Task.class);
        
        // Get open tasks
        ResponseEntity<List<Task>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Task>>() {}
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    @DisplayName("Should update a task")
    void testUpdateTask() {
        // Create a task
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskRequest> createRequest = new HttpEntity<>(sampleRequest, headers);
        
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(baseUrl, createRequest, Task.class);
        UUID taskId = createResponse.getBody().getId();
        
        // Update the task
        TaskRequest updateRequest = TaskRequest.builder()
                .title("Updated Title")
                .description("Updated Description")
                .startTime(now.plusDays(1))
                .aiPrompt("Updated AI Prompt")
                .build();
        
        HttpEntity<TaskRequest> updateEntity = new HttpEntity<>(updateRequest, headers);
        
        ResponseEntity<Task> updateResponse = restTemplate.exchange(
                baseUrl + "/" + taskId,
                HttpMethod.PUT,
                updateEntity,
                Task.class
        );
        
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals(taskId, updateResponse.getBody().getId());
        assertEquals(updateRequest.getTitle(), updateResponse.getBody().getTitle());
        assertEquals(updateRequest.getDescription(), updateResponse.getBody().getDescription());
        assertEquals(updateRequest.getStartTime(), updateResponse.getBody().getStartTime());
        assertEquals(updateRequest.getAiPrompt(), updateResponse.getBody().getAiPrompt());
        
        // Get the updated task
        ResponseEntity<Task> getResponse = restTemplate.getForEntity(baseUrl + "/" + taskId, Task.class);
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(taskId, getResponse.getBody().getId());
        assertEquals(updateRequest.getTitle(), getResponse.getBody().getTitle());
        assertEquals(updateRequest.getDescription(), getResponse.getBody().getDescription());
    }
    
    @Test
    @DisplayName("Should return 404 when task not found")
    void testGetNonExistentTask() {
        // Try to get a non-existent task
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<Object> response = restTemplate.getForEntity(
                baseUrl + "/" + nonExistentId,
                Object.class
        );
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
} 