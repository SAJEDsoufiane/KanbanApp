package com.telecom.applidistribuees.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
public class Developer {

	@Id
	@GeneratedValue
	private Long id;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private LocalDate startContract;
	
	
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "developers")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Task> tasks;

	public Developer() {
		this.tasks = new HashSet<Task>();
	}
	

}
