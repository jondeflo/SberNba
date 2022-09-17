package ru.sbernba.interntask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "s_groups", schema = "public")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@JoinColumn(name = "group", nullable = false)
	@JsonProperty("name")
	private String name;

	public String getName() { return name; }

}
