package com.taskmanager.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.taskmanager.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	Usuario findByUsername(String username);
}
