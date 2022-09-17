package ru.sbernba.interntask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "s_rates", schema = "public")
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@Column(name = "rate")
	@JsonProperty("rate")
	private Integer rate;

	@Column(name = "description")
	@JsonProperty("description")
	private String description;

	public Integer getRate() { return rate; }
	public String getDescription() { return description; }

}
