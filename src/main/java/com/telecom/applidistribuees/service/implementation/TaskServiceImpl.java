	package com.telecom.applidistribuees.service.implementation;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.telecom.applidistribuees.dao.TaskRepository;
import com.telecom.applidistribuees.dao.TaskStatusRepository;
import com.telecom.applidistribuees.dao.TaskTypeRepository;
import com.telecom.applidistribuees.model.ChangeLog;
import com.telecom.applidistribuees.model.Task;
import com.telecom.applidistribuees.model.TaskStatus;
import com.telecom.applidistribuees.model.TaskType;
import com.telecom.applidistribuees.service.TaskService;
import com.telecom.applidistribuees.utils.Constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
	private final TaskRepository taskRepository;
	private final TaskStatusRepository taskStatusRepository;
	private final TaskTypeRepository taskTypeRepository;

	@Override
	@Transactional(readOnly = true)
	public Collection<Task> findAllTasks() {
		return this.taskRepository.findAll();
	}
 
	@Override
	@Transactional(readOnly = true)
	public Collection<TaskType> findAllTaskTypes() {
		return this.taskTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<TaskStatus> findAllTaskStatus() {
		return this.taskStatusRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Task findTask(Long id) {
		return this.taskRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public TaskType findTaskType(Long id) {
		return this.taskTypeRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public TaskStatus findTaskStatus(Long id) {
		return this.taskStatusRepository.findById(id).get();
	}

	@Override
	public Task createTask(Task task) {
		Task newTask = taskRepository.save(task);
		return newTask;
	}

	@Override
	public Task deleteTask(Task task) {
		Task existingTask = taskRepository.findById(task.getId()).get();
		if (existingTask != null)
			taskRepository.delete(existingTask);
		return task;
	}

	@Override
	@Transactional(readOnly = true)
	public TaskStatus getFollowingTask(TaskStatus currentTaskStatus) {
		TaskStatus followingTask = null;

		TaskStatus todo = this.findTaskStatus(Constants.TASK_STATUS_ID_TODO);
		TaskStatus doing = this.findTaskStatus(Constants.TASK_STATUS_ID_DOING);
		TaskStatus test = this.findTaskStatus(Constants.TASK_STATUS_ID_TEST);
		TaskStatus done = this.findTaskStatus(Constants.TASK_STATUS_ID_DONE);

		if (currentTaskStatus.equals(todo)) {
			followingTask = doing;
		} else if (currentTaskStatus.equals(doing)) {
			followingTask = test;
		} else if (currentTaskStatus.equals(test)) {
			followingTask = done;
		} else if (currentTaskStatus.equals(done)) {
			followingTask = null;
		}
		return followingTask;
	}

	@Override
	@Transactional(readOnly = true)
	public TaskStatus getPreviousTask(TaskStatus currentTaskStatus) {
		TaskStatus previousTask = null;

		TaskStatus todo = this.findTaskStatus(Constants.TASK_STATUS_ID_TODO);
		TaskStatus doing = this.findTaskStatus(Constants.TASK_STATUS_ID_DOING);
		TaskStatus test = this.findTaskStatus(Constants.TASK_STATUS_ID_TEST);
		TaskStatus done = this.findTaskStatus(Constants.TASK_STATUS_ID_DONE);

		if (currentTaskStatus.equals(todo)) {
			previousTask = null;
		} else if (currentTaskStatus.equals(doing)) {
			previousTask = todo;
		} else if (currentTaskStatus.equals(test)) {
			previousTask = doing;
		} else if (currentTaskStatus.equals(done)) {
			previousTask = test;
		}
		return previousTask;
	}

	@Override
	@Transactional(readOnly = true)
	public Task updateTaskStatus(Task task, TaskStatus nextTaskStatus) {
		task = this.taskRepository.save(task);
		ChangeLog changeLog = new ChangeLog();
		changeLog.setOccured(LocalDateTime.now());
		changeLog.setSourceStatus(task.getStatus());
		changeLog.setTargetStatus(nextTaskStatus);
		task.setStatus(nextTaskStatus);
		task.addChangeLog(changeLog);
		return task;
	}

	@Override
	@Transactional(readOnly = true)
	public Task moveRightTask(Task task) {
		TaskStatus nextTaskStatus = this.getFollowingTask(task.getStatus());
		Task newTask = this.updateTaskStatus(task, nextTaskStatus);
		return newTask;
	}

	@Override
	@Transactional(readOnly = true)
	public Task moveLeftTask(Task task) {
		TaskStatus previousTaskStatus = this.getPreviousTask(task.getStatus());
		Task newTask = this.updateTaskStatus(task, previousTaskStatus);
		return newTask;
	}
}
