package net.afo.todoappdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
	List<TodoEntity> findByUsername(String name);
	List<TodoEntity> findByText(String text);
	List<TodoEntity> findByStatus(EStatus status);
}
