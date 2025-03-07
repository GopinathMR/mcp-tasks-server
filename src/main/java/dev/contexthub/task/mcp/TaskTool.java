package dev.contexthub.task.mcp;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import dev.contexthub.task.dto.TaskRequest;
import dev.contexthub.task.model.Task;
import dev.contexthub.task.service.TaskService;

@Component
public class TaskTool {

    private final TaskService taskService;

    public TaskTool(TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool
    public String scheduleTask(
        @ToolParam(description = "The title of the task")
        String title,
        @ToolParam(description = "description of the task")
        String description,
        @ToolParam(description = "The start time of the task")
        LocalDateTime startTime,
        @ToolParam(description = "The AI prompt to use for the task")
        String aiPrompt) {
        TaskRequest taskRequest = new TaskRequest(title, description, startTime, aiPrompt);
        Task task = taskService.createTask(taskRequest);
        return "Task created with Id : " + task.getId();
    }
}
