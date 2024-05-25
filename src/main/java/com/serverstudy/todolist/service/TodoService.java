package com.serverstudy.todolist.service;

import com.serverstudy.todolist.domain.Todo;
import com.serverstudy.todolist.domain.enums.Priority;
import com.serverstudy.todolist.dto.request.TodoReq.TodoPost;
import com.serverstudy.todolist.dto.request.TodoReq.TodoPut;
import com.serverstudy.todolist.dto.response.TodoRes;
import com.serverstudy.todolist.exception.CustomException;
import com.serverstudy.todolist.exception.ErrorCode;
import com.serverstudy.todolist.repository.TodoRepository;
import com.serverstudy.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.serverstudy.todolist.exception.ErrorCode.TODO_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public long create(TodoPost todoPost, Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Todo todo = todoPost.toEntity(userId);

        return todoRepository.save(todo).getId();
    }

    public List<TodoRes> findAllByRequirement(String priorityName, Long userId) {

        Priority priority = (priorityName == null)
                ? null
                : Priority.valueOf(priorityName);

        List<Todo> todoList = todoRepository.findAllByUserIdAndPriorityOrderByProgress(userId, priority);

        return todoList.stream().map(todo ->
                TodoRes.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .deadline(todo.getDeadline())
                        .priority(todo.getPriority())
                        .progress(todo.getProgress())
                        .build()
        ).toList();
    }

    @Transactional
    public long update(TodoPut todoPut, Long todoId) {

        Todo todo = getTodo(todoId);

        todo.updateTodo(todoPut);

        return todo.getId();
    }

    @Transactional
    public long switchProgress(long todoId) {

        Todo todo = getTodo(todoId);

        todo.switchProgress();

        return todo.getId();
    }

    @Transactional
    public void delete(Long todoId) {

        todoRepository.deleteById(todoId);
    }

    private Todo getTodo(Long todoId) {

        return todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(TODO_NOT_FOUND));
    }

}
