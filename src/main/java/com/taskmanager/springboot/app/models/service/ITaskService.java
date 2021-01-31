package com.taskmanager.springboot.app.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taskmanager.springboot.app.models.entity.Task;

public interface ITaskService {

	Page<Task> findAll(Pageable pageable);

	void save(Task task);
	
	Task findOne(Long id);
	
	void delete(Long id);

}
