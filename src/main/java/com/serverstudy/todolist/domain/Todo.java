package com.serverstudy.todolist.domain;

import com.serverstudy.todolist.domain.enums.Priority;
import com.serverstudy.todolist.domain.enums.Progress;
import com.serverstudy.todolist.dto.request.TodoReq.TodoPut;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="text")
    private String description;

    private LocalDateTime deadline;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Progress progress;

    private Long userId;

    @Builder
    private Todo(String title, String description, LocalDateTime deadline, Priority priority, Progress progress, long userId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.progress = progress;
        this.userId = userId;
    }

    public void switchProgress() {
        if (this.progress.equals(Progress.TODO)) this.progress = Progress.DONE;
        else this.progress = Progress.TODO;
    }

    public void updateTodo(TodoPut todoPut) {
        this.title = todoPut.getTitle();
        this.description = todoPut.getDescription();
        this.deadline = todoPut.getDeadline();
        this.priority = todoPut.getPriority();
        this.progress = todoPut.getProgress();
    }
}
