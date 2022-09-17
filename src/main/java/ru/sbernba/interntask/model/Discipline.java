package ru.sbernba.interntask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "s_disciplines", schema = "public")
public class Discipline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@Column(name = "name")
	@JsonProperty("name")
	private String name;

	public Long getId() { return id; }
	public String getName() { return name; }
}
