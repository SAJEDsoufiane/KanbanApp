package com.telecom.applidistribuees.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.applidistribuees.model.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {

}
