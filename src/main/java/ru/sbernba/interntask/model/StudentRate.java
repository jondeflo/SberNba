package ru.sbernba.interntask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "rates", schema = "public")
public class StudentRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "student_id")
	@JsonProperty("student id")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "discipline_id")
	@NotNull
	private Discipline discipline;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "rate_id")
	@NotNull
	private Rate rate;

	@Column(name = "timestamp")
	private java.sql.Timestamp timestamp;

	@Column(name = "description")
	private String description;

	public void setStudent(Student student) { this.student = student; }
	public void setDiscipline(Discipline discipline) { this.discipline = discipline; }
	public void setRate(Rate rate) { this.rate = rate; }
	public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
	public void setDescription(String description) { this.description = description; }
	public Rate getRate() { return rate; }

}
