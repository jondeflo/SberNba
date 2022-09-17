package ru.sbernba.interntask.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentDto {

	@NotEmpty(message = "Name must not be empty")
	@Size(min = 1, max = 255, message = "Name must contain from 1 to 255 characters")
	private String name;

	@NotEmpty(message = "Name must not be empty")
	@Size(min = 1, max = 255, message = "Surname must contain from 1 to 255 characters")
	private String surname;

	@NotNull(message = "Age must not be empty")
	@Range(min = 1, max = 255, message = "Age must be between 1 and 255")
	private Integer age;

	@NotEmpty(message = "Group name must not be empty")
	@Size(min = 1, max = 255, message = "Group name must contain from 1 to 255 characters")
	private String group;

	public String getName() { return name; }
	public String getSurname() { return surname; }
	public Integer getAge() { return age; }
	public String getGroup() { return group; }

}
