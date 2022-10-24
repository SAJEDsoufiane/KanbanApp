package com.telecom.applidistribuees.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telecom.applidistribuees.model.Developer;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
