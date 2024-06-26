package com.serverstudy.todolist.dto.request;

import com.serverstudy.todolist.common.Enum;
import com.serverstudy.todolist.domain.Todo;
import com.serverstudy.todolist.domain.enums.Priority;
import com.serverstudy.todolist.domain.enums.Progress;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public interface TodoReq {

    @Schema(description = "투두 생성 요청 DTO")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class TodoPost {

        @Schema(title = "제목", description = "공백으로 입력 불가능",
                example = "컴퓨터공학개론 레포트")
        @NotEmpty(message = "제목을 작성해주세요.")
        private String title;

        @Schema(title = "설명", description = "미기입 또는 자유롭게 입력",
                example = "주제: 컴퓨터공학이란 무엇인가?, 분량: 3장 이상")
        private String description;

        @Schema(title = "마감 기한", description = "미기입 또는 (yyyy-MM-ddTHH:mm:00Z) 형태로 년,월,일,시,분 입력",
                example = "2024-05-15T23:59:00Z")
        private LocalDateTime deadline;

        @Schema(title = "우선 순위", description = "(High|Medium|Low) 중 하나를 대소문자 구분 없이 입력",
                example = "High", allowableValues = {"High", "Medium", "Low"})
        @NotNull(message = "값이 비어있을 수 없습니다. 값을 입력해주세요.")
        @Enum(enumClass = Priority.class, ignoreCase = true)
        private String priority;

        @Schema(title = "진행 상황", description = "(TODO|DONE) 중 하나를 대소문자 구분 없이 입력",
                example = "TODO", allowableValues = {"TODO", "DONE"})
        @NotNull(message = "값이 비어있을 수 없습니다. 값을 입력해주세요.")
        @Enum(enumClass = Progress.class, ignoreCase = true)
        private String progress;

        public Todo toEntity(long userId) {
            return Todo.builder()
                    .title(title == null ? "" : title)
                    .description(description == null ? "" : description)
                    .deadline(deadline)
                    .priority(Priority.valueOf(priority))
                    .progress(Progress.valueOf(progress))
                    .userId(userId)
                    .build();
        }

    }

    @Schema(description = "투두 수정 요청 DTO")
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class TodoPut {

        @Schema(title = "제목", description = "공백으로 입력 불가능",
                example = "앱센터 발표")
        @NotEmpty(message = "제목을 작성해주세요.")
        private String title;

        @Schema(title = "설명", description = "미기입 또는 자유롭게 입력",
                example = "주제: 스프링이란?, 비고: GitHub에 업로드 ")
        private String description;

        @Schema(title = "마감 기한", description = "미기입 또는 (yyyy-MM-ddTHH:mm:00Z) 형태로 년,월,일,시,분 입력",
                example = "2024-05-20T12:00:00Z")
        private LocalDateTime deadline;

        @Schema(title = "우선 순위", description = "(High|Medium|Low) 중 하나를 대소문자 구분 없이 입력",
                example = "High", allowableValues = {"High", "Medium", "Low"})
        @NotNull(message = "값이 비어있을 수 없습니다. 값을 입력해주세요.")
        @Enum(enumClass = Priority.class, ignoreCase = true)
        private String priority;

        @Schema(title = "진행 상황", description = "(TODO|DONE) 중 하나를 대소문자 구분 없이 입력",
                example = "TODO", allowableValues = {"TODO", "DONE"})
        @NotNull(message = "값이 비어있을 수 없습니다. 값을 입력해주세요.")
        @Enum(enumClass = Progress.class, ignoreCase = true)
        private String progress;

        public String getTitle() {
            return title == null ? "" : title;
        }

        public String getDescription() {
            return description == null ? "" : description;
        }

        public Priority getPriority() {
            return Priority.valueOf(priority);
        }

        public Progress getProgress() {
            return Progress.valueOf(progress);
        }
    }

}
