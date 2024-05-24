package com.serverstudy.todolist.repository;

import com.serverstudy.todolist.domain.Todo;
import com.serverstudy.todolist.domain.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    List<Todo> findAllByUserIdAndPriorityOrderByProgressDesc(long userId, Priority priority);
    List<Todo> findAllByUserId(long userId);
}
