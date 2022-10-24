package com.telecom.applidistribuees.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.telecom.applidistribuees.dao.DeveloperRepository;
import com.telecom.applidistribuees.model.Developer;
import com.telecom.applidistribuees.service.DeveloperService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

	private final DeveloperRepository developerRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Developer> findAllDevelopers() {
		return this.developerRepository.findAll();
	}

	@Override
	public Optional<Developer> findDeveloperById(Long id) {
		return this.developerRepository.findById(id);
	}
}
