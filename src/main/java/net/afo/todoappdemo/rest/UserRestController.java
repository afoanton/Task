package net.afo.todoappdemo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.afo.todoappdemo.dto.TodoDto;
import net.afo.todoappdemo.dto.UserDto;
import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.TodoEntity;
import net.afo.todoappdemo.model.UserEntity;
import net.afo.todoappdemo.service.TodoService;
import net.afo.todoappdemo.service.UserService;

@RestController
@RequestMapping(value = "/api/todo/")
public class UserRestController {
    private final UserService userService;
    private final TodoService todoService;

    @Autowired
    public UserRestController(UserService userService, TodoService todoService) {
        this.userService = userService;
		this.todoService = todoService;       
    }

    @GetMapping(value = "user/{id}") //TODO: add registration for non-admin users (now works)
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        UserEntity user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "note/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable(name = "id") Long id){
        TodoEntity todo = todoService.findById(id);

        if(todo == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }        
        
        TodoDto result = TodoDto.toUser(todo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping(value = "note")
    public ResponseEntity<List<TodoDto>> getAllNotes(){
        List<TodoEntity> notes = todoService.getAll();

        if(notes == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }        
        
        List<TodoDto> result = new ArrayList<>();

        notes.forEach(todo -> {
            result.add(TodoDto.toUser(todo));
        });
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping(value = "activenotes")
    public ResponseEntity<List<TodoDto>> getAllActiveNotes(){
        List<TodoEntity> notes = todoService.getAll(EStatus.ACTIVE);

        if(notes == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }        
        
        List<TodoDto> result = new ArrayList<>();

        notes.forEach(todo -> {
            result.add(TodoDto.toUser(todo));
        });
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping(value = "createnote")
    public ResponseEntity<TodoDto> createNote(@RequestBody TodoDto requestDto) {
    	TodoEntity resultEntity = requestDto.fromUser();
    	
    	TodoDto resultDto = TodoDto.toUser(todoService.create(resultEntity));
    	return ResponseEntity.ok(resultDto);
        
    }
    @PostMapping(value = "updatenote/{id}")
    public ResponseEntity<TodoDto> updateNote(@PathVariable(name = "id") Long id , @RequestBody TodoDto requestDto) {
    	TodoEntity resultEntity = requestDto.fromUser();
    	
    	TodoDto resultDto = TodoDto.toUser(todoService.update(id, resultEntity));
    	return ResponseEntity.ok(resultDto);
        
    }
    @DeleteMapping(value = "updatenote/{id}")
    public HttpStatus deleteNote(@PathVariable(name = "id") Long id) {
    	todoService.delete(id);
    	return HttpStatus.OK;
        
    }
}
