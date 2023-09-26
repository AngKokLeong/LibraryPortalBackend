package com.libraryportal.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryportal.restapi.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    
}
