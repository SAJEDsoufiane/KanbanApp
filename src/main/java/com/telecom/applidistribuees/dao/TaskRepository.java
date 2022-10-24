package com.telecom.applidistribuees.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.applidistribuees.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
