 	package com.telecom.applidistribuees.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.telecom.applidistribuees.model.Task;
import com.telecom.applidistribuees.model.TaskStatus;
import com.telecom.applidistribuees.model.TaskType;

@Service
public interface TaskService {
Collection<Task> findAllTasks();
	
	Task findTask(Long id);

	Task moveRightTask(Task task);
	
	Task moveLeftTask(Task task);
	
	//	Additional methods
	
	Collection<TaskType> findAllTaskTypes();

	Collection<TaskStatus> findAllTaskStatus();

	TaskType findTaskType(Long id);

	TaskStatus findTaskStatus(Long id);	
	
	Task updateTaskStatus(Task task, TaskStatus nextTaskStatus);

	TaskStatus getFollowingTask(TaskStatus currentTaskStatus);
	
	TaskStatus getPreviousTask(TaskStatus currentTaskStatus);
	
	Task createTask(Task task);
	
	Task deleteTask(Task task);
}
