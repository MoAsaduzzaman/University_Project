package com.pulse.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pulse.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	
	
}
