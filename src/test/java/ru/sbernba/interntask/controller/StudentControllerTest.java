package ru.sbernba.interntask.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.sbernba.interntask.model.*;
import ru.sbernba.interntask.repository.*;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ContextConfiguration(initializers = {StudentControllerTest.Initializer.class})
@Testcontainers
public class StudentControllerTest {


	@Autowired
	StudentRepository studentRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	StudentRateRepository studentRateRepository;

	@Autowired
	DisciplineRepository disciplineRepository;

	@Autowired
	RateRepository rateRepository;

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
			.withDatabaseName("db")
			.withUsername("user")
			.withPassword("pass");

	@Test
	@Transactional
	public void studentCountMustBeCorrect() {

		List<Student> studentList = studentRepository.findBy();
		assertEquals(10, studentList.size());
	}

	@Test
	@Transactional
	public void studentCountAfterAddOneStudentMustBeCorrect() {

		Student student = new Student();
		Group group = groupRepository.findFirstByName("Genius");

		student.setName("John");
		student.setSurname("Doe");
		student.setAge(77);
		student.setGroup(group);

		studentRepository.save(student);

		List<Student> studentList = studentRepository.findBy();
		assertEquals(11, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithNameMahmudIsNotPresent() {

		List<Student> studentList = studentRepository.findAllByName("Mahmud");
		assertEquals(0, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithNameOlegMustBeOnlyOne() {

		List<Student> studentList = studentRepository.findAllByName("Oleg");
		assertEquals(1, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithNameAnnaMustBeTwo() {

		List<Student> studentList = studentRepository.findAllByName("Anna");
		assertEquals(2, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithSurnameSemenovMustBeTwo() {

		List<Student> studentList = studentRepository.findAllBySurname("Semenov");
		assertEquals(2, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithAgeTwentyOneMustBeThree() {

		List<Student> studentList = studentRepository.findAllByAge(21);
		assertEquals(3, studentList.size());
	}

	@Test
	@Transactional
	public void studentWithSameNameMustBeOrderedBySurnameAsc() {

		List<Student> studentList = studentRepository.findAllByNameOrderBySurnameAsc("Anna");

		assertAll("Nepavlova should be first, Pavlova second",
				() -> assertEquals(studentList.get(0).getSurname(), "Nepavlova"),
				() -> assertEquals(studentList.get(1).getSurname(), "Pavlova")
		);
	}

	@Test
	@Transactional
	public void studentWithSameNameMustBeOrderedBySurnameDesc() {

		List<Student> studentList = studentRepository.findAllByNameOrderBySurnameDesc("Anna");

		assertAll("Pavlova should be first, Nepavlova second",
				() -> assertEquals(studentList.get(0).getSurname(), "Pavlova"),
				() -> assertEquals(studentList.get(1).getSurname(), "Nepavlova")
		);
	}

	@Test
	@Transactional
	public void ratesCountMustBeCorrect() {

		Student student = studentRepository.findFirstById(1L);
		Discipline discipline = disciplineRepository.findFirstByName("Math");
		Rate rate = rateRepository.findFirstByRate(4);
		StudentRate studentRate = new StudentRate();
		studentRate.setStudent(student);
		studentRate.setDiscipline(discipline);
		studentRate.setRate(rate);
		studentRate.setTimestamp(new Timestamp(System.currentTimeMillis()));
		studentRate.setDescription(rate.getDescription());
		studentRateRepository.save(studentRate);

		List<StudentRate> rateList = studentRateRepository.findBy();
		assertEquals(22, rateList.size());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword(),
					"spring.liquibase.enabled=true"
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

}
