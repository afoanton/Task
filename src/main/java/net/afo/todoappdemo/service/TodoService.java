package net.afo.todoappdemo.service;

import java.util.List;

import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.TodoEntity;

public interface TodoService {

	    List<TodoEntity> getAll();
	    
	    List<TodoEntity> getAll(EStatus status);
	    
	    List<TodoEntity> findByAutor(String autor);

	    TodoEntity findByText(String text);

	    TodoEntity findById(Long id);

	    void delete(Long id);
	    
	    TodoEntity update(Long id, TodoEntity todo);
	    
	    TodoEntity create(TodoEntity todo);

}
