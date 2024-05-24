package com.serverstudy.todolist.repository;

import com.serverstudy.todolist.domain.Todo;
import com.serverstudy.todolist.domain.enums.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    @Query("SELECT t FROM Todo t WHERE t.userId = :userId AND t.priority = :priority " +
            "ORDER BY CASE t.progress WHEN 'TODO' THEN 0 WHEN 'DONE' THEN 1 END")
    List<Todo> findAllByUserIdAndPriorityOrderByProgress(long userId, Priority priority);
    List<Todo> findAllByUserId(long userId);
}
