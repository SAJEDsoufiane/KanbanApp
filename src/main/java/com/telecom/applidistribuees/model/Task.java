package com.telecom.applidistribuees.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor

public class Task {
	
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private Integer nbHoursForecast;
	
	
	private Integer nbHoursReal;
	private LocalDate created;

	@ManyToOne
	private TaskType type;

	@ManyToOne
	private TaskStatus status;

	@ManyToMany(fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Developer> developers;

	@OneToMany(mappedBy = "task", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<ChangeLog> changeLogs;
	
	public Task() {
		this.developers = new HashSet<Developer>();
	    this.changeLogs = new HashSet<ChangeLog>();
	}

	public void addDeveloper(Developer dev) {
		dev.getTasks().add(this);
		this.developers.add(dev);
	}

	public void addChangeLog(ChangeLog changeLog) {
		changeLog.setTask(this);
		this.changeLogs.add(changeLog);
	}

	public void clearChangeLogs() {
		for (ChangeLog changeLog : this.changeLogs) {
			changeLog.setTask(null);
		}
	}

}
