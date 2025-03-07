package dev.contexthub.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private String aiPrompt;
    private boolean completed;

    public static Task createNew(String title, String description, LocalDateTime startTime, String aiPrompt) {
        return Task.builder()
                .id(UUID.randomUUID())
                .title(title)
                .description(description)
                .startTime(startTime)
                .aiPrompt(aiPrompt)
                .completed(false)
                .build();
    }
} 