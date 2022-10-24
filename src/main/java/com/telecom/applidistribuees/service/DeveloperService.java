package com.telecom.applidistribuees.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.telecom.applidistribuees.model.Developer;

@Service
public interface DeveloperService {
	java.util.List<Developer> findAllDevelopers();
	Optional<Developer> findDeveloperById(Long id);
}
