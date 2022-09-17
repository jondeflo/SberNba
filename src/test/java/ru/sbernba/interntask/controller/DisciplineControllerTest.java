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
import ru.sbernba.interntask.model.Discipline;
import ru.sbernba.interntask.repository.DisciplineRepository;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(initializers = {DisciplineControllerTest.Initializer.class})
@Testcontainers
public class DisciplineControllerTest {

	@Autowired
	DisciplineRepository disciplineRepository;

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
			.withDatabaseName("db")
			.withUsername("user")
			.withPassword("pass");

	@Test
	@Transactional
	public void disciplineCountMustBeCorrect() {

		List<Discipline> disciplineList = disciplineRepository.findBy();
		assertEquals(5, disciplineList.size());
	}

	@Test
	@Transactional
	public void disciplineNamesMustBeCorrect() {

		List<Discipline>  disciplineList = disciplineRepository.findBy();
		String[] expectedDisciplineNames = {"Math", "Physics", "Chemistry", "Biology", "Medicine"};
		assertArrayEquals(expectedDisciplineNames, disciplineList.stream().map(discipline -> discipline.getName()).toArray());

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
