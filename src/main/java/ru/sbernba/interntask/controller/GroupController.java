package ru.sbernba.interntask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbernba.interntask.model.Group;
import ru.sbernba.interntask.model.Student;
import ru.sbernba.interntask.repository.GroupRepository;
import ru.sbernba.interntask.repository.StudentRepository;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	StudentRepository studentRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAllGroups() {

		ArrayList<Group> groups = groupRepository.findBy();
		return new ResponseEntity<>(groups, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findStudentsByGroupId(@PathVariable Long id) {

		ArrayList<Student> students = studentRepository.findAllByGroupId(id);
		return new ResponseEntity<>(students, new HttpHeaders(), HttpStatus.OK);
	}
}
