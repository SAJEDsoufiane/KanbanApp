package com.telecom.applidistribuees.utils;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;

import com.telecom.applidistribuees.dao.DeveloperRepository;
import com.telecom.applidistribuees.dao.TaskRepository;
import com.telecom.applidistribuees.dao.TaskStatusRepository;
import com.telecom.applidistribuees.dao.TaskTypeRepository;
import com.telecom.applidistribuees.model.Developer;
import com.telecom.applidistribuees.model.Task;
import com.telecom.applidistribuees.model.TaskStatus;
import com.telecom.applidistribuees.model.TaskType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoadDatabase {

	@Bean
	CommandLineRunner initTestDatabase(TaskStatusRepository taskStatusRepository,
			DeveloperRepository developerRepository, TaskTypeRepository taskTypeRepository,
			TaskRepository taskRepository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				initTaskStatus(taskStatusRepository);
				initTaskTypes(taskTypeRepository);				
				Developer dev = initDevelopers(developerRepository);
				Task task1 = initTasks(taskRepository, dev, taskStatusRepository, taskTypeRepository,"Fix npm vulnerability");
				Task task2 = initTasks(taskRepository, dev, taskStatusRepository, taskTypeRepository,"Audit server");
			}
		};
	}

	private void initTaskStatus(TaskStatusRepository taskStatusRepository) {

		TaskStatus todoTask = new TaskStatus(Constants.TASK_STATUS_ID_TODO, Constants.TASK_STATUS_LABEL_TODO);
		taskStatusRepository.save(todoTask);
		log.info("New item saved to DB " + todoTask);

		TaskStatus doingTask = new TaskStatus(Constants.TASK_STATUS_ID_DOING, Constants.TASK_STATUS_LABEL_DOING);
		taskStatusRepository.save(doingTask);
		log.info("New item saved to DB " + doingTask);

		TaskStatus testTask = new TaskStatus(Constants.TASK_STATUS_ID_TEST, Constants.TASK_STATUS_LABEL_TEST);
		taskStatusRepository.save(testTask);
		log.info("New item saved to DB " + testTask);

		TaskStatus doneTask = new TaskStatus(Constants.TASK_STATUS_ID_DONE, Constants.TASK_STATUS_LABEL_DONE);
		taskStatusRepository.save(doneTask);
		log.info("New item saved to DB " + doneTask);

	}

	private void initTaskTypes(TaskTypeRepository taskTypeRepository) {
		// Seeding TaskStatus states and data to the DB and loging some info
		TaskType bugTask = new TaskType(Constants.TASK_TYPES_ID_BUG, Constants.TASK_TYPES_LABEL_BUG);
		taskTypeRepository.save(bugTask);
		log.info("New item saved to DB" + bugTask);

		TaskType featureTask = new TaskType(Constants.TASK_TYPES_ID_FEATURE, Constants.TASK_TYPES_LABEL_FEATURE);
		taskTypeRepository.save(featureTask);
		log.info("New item saved to DB" + featureTask);
	}

	private Developer initDevelopers(DeveloperRepository developerRepository) {
		// Seeding Developers data to the DB

		Developer soufiane = new Developer();
		soufiane.setFirstname("soufiane");
		soufiane.setEmail("soufiane.sajed@telecom-st-etienne.fr");
		soufiane.setLastname("sajed");
		soufiane.setPassword("password");
		soufiane.setStartContract(LocalDate.of(2022, Month.OCTOBER, 24));
		Developer dev = developerRepository.save(soufiane);
		log.info("new developer saved to DB " + soufiane);
		return dev;
	}

	private Task initTasks(TaskRepository taskRepository, Developer dev, TaskStatusRepository taskStatusRepository,
			TaskTypeRepository taskTypeRepository,String title) {
		Task taskOne = new Task();
		taskOne.addDeveloper(dev);
		taskOne.setTitle(title);
		taskOne.setCreated(LocalDate.now());
		taskOne.setNbHoursForecast(0);
		taskOne.setNbHoursReal(0);
		taskOne.setStatus(taskStatusRepository.findById(Constants.TASK_STATUS_ID_TODO).orElse(null));
		taskOne.setType(taskTypeRepository.findById(Constants.TASK_TYPES_ID_BUG).orElse(null));
		Task task = taskRepository.save(taskOne);
		log.info("new Task saved to DB" + taskOne);
		return task;
	}
}
