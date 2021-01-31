package com.taskmanager.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String task;
	private String completed = "0" ;
	public Task() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}
}
