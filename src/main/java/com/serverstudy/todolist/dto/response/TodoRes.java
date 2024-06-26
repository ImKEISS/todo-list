package com.serverstudy.todolist.dto.response;

import com.serverstudy.todolist.domain.enums.Priority;
import com.serverstudy.todolist.domain.enums.Progress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "투두 응답 DTO")
@Getter
public class TodoRes {

    @Schema(title = "투두 Id", description = "투두 Id", example = "1")
    private final Long id;

    @Schema(title = "제목", description = "제목", example = "컴퓨터공학개론 레포트")
    private final String title;

    @Schema(title = "설명", description = "설명", example = "주제: 컴퓨터공학이란 무엇인가?, 분량: 3장 이상")
    private final String description;

    @Schema(title = "마감 기한", description = "마감 기한", example = "2024-05-15T23:59:00Z")
    private final LocalDateTime deadline;

    @Schema(title = "우선 순위", description = "우선 순위", example = "High")
    private final Priority priority;

    @Schema(title = "진행 상황", description = "진행 상황", example = "DONE")
    private final Progress progress;

    @Builder
    private TodoRes(Long id, String title, String description, LocalDateTime deadline, Priority priority, Progress progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.progress = progress;
    }
}