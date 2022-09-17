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
import ru.sbernba.interntask.model.Group;
import ru.sbernba.interntask.model.Student;
import ru.sbernba.interntask.repository.GroupRepository;
import ru.sbernba.interntask.repository.StudentRepository;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(initializers = {GroupControllerTest.Initializer.class})
@Testcontainers
public class GroupControllerTest {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	StudentRepository studentRepository;

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
			.withDatabaseName("db")
			.withUsername("user")
			.withPassword("pass");

	@Test
	@Transactional
	public void groupCountMustBeCorrect() {

		List<Group> groupList = groupRepository.findBy();
		assertEquals(5, groupList.size());
	}

	@Test
	@Transactional
	public void groupNamesMustBeCorrect() {

		List<Group> groupList = groupRepository.findBy();
		String[] expectedGroupNames = {"Genius", "Eggheads", "Averages", "Fools", "Brainless"};
		assertArrayEquals(expectedGroupNames, groupList.stream().map(group -> group.getName()).toArray());
	}

	@Test
	@Transactional
	public void countStudentInExactGroupMustBeCorrect() {

		List<Student> students = studentRepository.findAllByGroupId(2L);
		assertEquals(2, students.size());
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
