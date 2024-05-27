package com.serverstudy.todolist.controller;

import com.serverstudy.todolist.common.Enum;
import com.serverstudy.todolist.common.ExampleData;
import com.serverstudy.todolist.domain.enums.Priority;
import com.serverstudy.todolist.dto.request.TodoReq.TodoPost;
import com.serverstudy.todolist.dto.response.TodoRes;
import com.serverstudy.todolist.exception.ErrorResponse;
import com.serverstudy.todolist.security.SecurityUser;
import com.serverstudy.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.serverstudy.todolist.dto.request.TodoReq.TodoPut;

@Tag(name = "Todo", description = "Todo API 입니다.")
@Validated
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TodoController implements ExampleData {

    private final TodoService todoService;

    @Operation(summary = "투두 생성", description = "새로운 투두를 생성합니다.", responses = {
            @ApiResponse(responseCode = "201", description = "투두 생성 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 입력", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "INVALID_PARAMETER", value = INVALID_PARAMETER_DATA),
            })),
            @ApiResponse(responseCode = "404", description = "유저가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "USER_NOT_FOUND", value = USER_NOT_FOUND_DATA)
            }))
    })
    @PostMapping
    public ResponseEntity<Long> postTodo(@Valid @RequestBody TodoPost todoPost, @AuthenticationPrincipal SecurityUser user) {

        Long todoId = todoService.create(todoPost, user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(todoId);
    }

    @Operation(summary = "투두 목록 조회", description = "조건에 맞는 투두 목록을 가져옵니다. 진행 상황이 'TODO' -> 'DONE' 순서로 정렬되며, 투두 id 순서대로 가져옵니다.", responses = {
            @ApiResponse(responseCode = "200", description = "투두 목록 조회 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 입력", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "INVALID_PARAMETER", value = INVALID_PARAMETER_DATA),
            }))
    })
    @GetMapping
    public ResponseEntity<List<TodoRes>> getTodosByRequirement(
            @Schema(title = "우선 순위", description = "(PRIMARY|SECONDARY|TERTIARY) 중 하나를 대소문자 구분 없이 입력",
                    example = "PRIMARY", allowableValues = {"PRIMARY", "SECONDARY", "TERTIARY"})
            @NotNull(message = "값이 비어있을 수 없습니다. 값을 입력해주세요.")
            @Enum(enumClass = Priority.class, ignoreCase = true) String priority
            , @AuthenticationPrincipal SecurityUser user) {

        List<TodoRes> responseList = todoService.findAllByRequirement(priority, user.getId());

        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "투두 수정", description = "해당 투두를 수정합니다. 수정을 원치 않는 값은 조회한 값을 그대로 넣어주세요.", parameters = {
            @Parameter(name = "todoId", description = "투두 id", example = "1")
    }, responses = {
            @ApiResponse(responseCode = "200", description = "투두 수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 입력", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "INVALID_PARAMETER", value = INVALID_PARAMETER_DATA),
            })),
            @ApiResponse(responseCode = "404", description = "투두가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "TODO_NOT_FOUND", value = TODO_NOT_FOUND_DATA)
            }))
    })
    @PutMapping("/{todoId}")
    public ResponseEntity<Long> putTodo(@Valid @RequestBody TodoPut todoPut, @PathVariable Long todoId) {

        Long updatedTodoId = todoService.update(todoPut, todoId);

        return ResponseEntity.ok(updatedTodoId);
    }

    @Operation(summary = "투두 진행 상황 수정", description = "해당 투두의 진행 상황을 변경합니다. TODO -> DONE -> TODO 순서로 바뀝니다. ", parameters = {
            @Parameter(name = "todoId", description = "투두 id", example = "1")
    }, responses = {
            @ApiResponse(responseCode = "200", description = "투두 진행 상황 수정 성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "투두가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class), examples = {
                    @ExampleObject(name = "TODO_NOT_FOUND", value = TODO_NOT_FOUND_DATA),
            }))
    })
    @PatchMapping("/{todoId}/progress")
    public ResponseEntity<Long> switchTodoProgress(@PathVariable Long todoId) {

        Long switchedTodoId = todoService.switchProgress(todoId);

        return ResponseEntity.ok(switchedTodoId);
    }

    @Operation(summary = "투두 삭제", description = "해당 투두를 삭제합니다.", parameters = {
            @Parameter(name = "todoId", description = "투두 id", example = "1")
    }, responses = {
            @ApiResponse(responseCode = "204", description = "투두 삭제 성공", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Long> deleteTodo(@PathVariable Long todoId) {

        todoService.delete(todoId);

        return ResponseEntity.noContent().build();
    }
}
