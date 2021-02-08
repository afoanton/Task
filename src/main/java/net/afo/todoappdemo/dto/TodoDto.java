package net.afo.todoappdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import net.afo.todoappdemo.model.EStatus;
import net.afo.todoappdemo.model.TodoEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoDto {
    private Long id;
    private String text;
    private String autor;
    private String status;

    public TodoEntity fromUser(){
    	TodoEntity todo = new TodoEntity();
        todo.setId(this.id);
        todo.setText(this.text);
        todo.setUsername(this.autor);
        if (this.status == null) todo.setStatus(EStatus.NOT_ACTIVE);
        else todo.setStatus(this.status.equals("ACTIVE") ? EStatus.ACTIVE : EStatus.NOT_ACTIVE);
        return todo;
    }

    public static TodoDto toUser(TodoEntity todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setAutor(todo.getUsername());
        todoDto.setText(todo.getText());
        todoDto.setStatus(todo.getStatus().equals(EStatus.ACTIVE) ? "ACTIVE" : "NOT_ACTIVE");
        return todoDto;
    }
}
