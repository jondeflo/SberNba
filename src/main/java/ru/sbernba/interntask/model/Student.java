package ru.sbernba.interntask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "students", schema = "public")
public class Student {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@JsonProperty("student id")
	private Long id;

	@Column(name = "name")
	@JsonProperty("name")
	private String name;

	@Column(name = "surname")
	@JsonProperty("surname")
	private String surname;

	@Column(name = "age")
	@JsonProperty("age")
	private Integer age;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "group_id")
	@NotNull
	private Group group;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StudentRate> studentRates;

	public void setName(String name) { this.name = name; }
	public void setSurname(String surname) { this.surname = surname; }
	public void setAge (Integer age) { this.age = age; }
	public void setGroup(Group group) { this.group = group; }
	public String getName() { return name; }
	public String getSurname() { return surname; }

}