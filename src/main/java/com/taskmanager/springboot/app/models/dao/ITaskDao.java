package com.taskmanager.springboot.app.models.dao;

import com.taskmanager.springboot.app.models.entity.Task;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ITaskDao extends PagingAndSortingRepository<Task, Long> {
}
