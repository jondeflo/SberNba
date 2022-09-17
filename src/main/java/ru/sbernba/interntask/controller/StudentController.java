package ru.sbernba.interntask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbernba.interntask.dto.StudentDto;
import ru.sbernba.interntask.repository.StudentRepository;
import ru.sbernba.interntask.service.StudentService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentService studentService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findStudentsByParams(@RequestParam (required = false) Map<String, String> requestData) {

		if (requestData.size() == 0) {
			return studentService.searchAllStudents();
		}
		return studentService.searchStudentsByParams(requestData);
	}

	@PostMapping
	public ResponseEntity<Object> addStudent(@Valid @RequestBody StudentDto studentData) {

		try {
			return studentService.addStudent(studentData);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request parameters", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/rates")
	public ResponseEntity<Object> addStudentRate(@RequestBody Map<String, String> rateData) {

		return studentService.addRates(rateData);
	}
}