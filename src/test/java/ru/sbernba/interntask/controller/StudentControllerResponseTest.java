package ru.sbernba.interntask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class StudentControllerResponseTest {

	@Autowired
	private StudentController studentController;

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	}

	@Test
	public void checkSuccessResponse() throws Exception {

		mockMvc.perform(get("/students"))
				.andExpect(status().isOk());
	}

	@Test
	public void checkSuccessSearchResponseWithFirstParam() throws Exception {

		mockMvc.perform(get("/students")
						.param("name", "Anna"))
				.andExpect(status().isOk());
	}

	@Test
	public void checkSuccessSearchResponseWithOrderParams() throws Exception {

		mockMvc.perform(get("/students")
						.param("name", "Anna")
						.param("orderby", "age")
						.param("dir", "asc"))
				.andExpect(status().isOk());
	}

	@Test
	public void checkBadSearchResponseWithoutDirectionParams() throws Exception {

		mockMvc.perform(get("/students")
						.param("name", "Anna")
						.param("orderby", "age"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void checkBadSearchResponseWithoutOrderParams() throws Exception {
		mockMvc.perform(get("/students")
						.param("name", "Anna")
						.param("dir", "desc"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void checkAddStudentResponse() throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("name", "John");
		map.put("surname", "Doe");
		map.put("age", "23");
		map.put("group", "Genius");

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(map))
						.characterEncoding("UTF-8"))
				.andExpect(status().isOk());
	}

	@Test
	public void checkAddStudentWithAbsentGroupBadResponse() throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("name", "John");
		map.put("surname", "Doe");
		map.put("age", "23");
		map.put("group", "Morons");

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(map))
						.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void checkAddStudentWithoutAgeBadResponse() throws Exception {

		Map<String, String> map = new HashMap<>();
		map.put("name", "John");
		map.put("surname", "Doe");
		map.put("group", "Genius");

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(map))
						.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void checkEmptyPostResponse() throws Exception {

		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(new HashMap<>()))
						.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest());
	}

}
