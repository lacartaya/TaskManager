package com.taskmanager.springboot.app.models.service;

import com.taskmanager.springboot.app.models.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.springboot.app.models.dao.ITaskDao;


@Service
public class TaskServiceImpl implements ITaskService {

	@Autowired
	private ITaskDao taskDao;

	@Override

	@Transactional
	public void save(Task task) {
		taskDao.save(task);
	}

	@Override
	@Transactional(readOnly = true)
	public Task findOne(Long id) {
		return taskDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		taskDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Task> findAll(Pageable pageable) {
		return taskDao.findAll(pageable);
	}


	
	
}
