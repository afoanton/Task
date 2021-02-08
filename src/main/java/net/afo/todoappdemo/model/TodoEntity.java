package net.afo.todoappdemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "todo")
@Data
public class TodoEntity extends BaseEntity{
	
	@Column(name = "username")
    private String username;

    @Column(name = "text")
    private String text;
}
