package ru.sbernba.interntask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sbernba.interntask.model.Discipline;
import ru.sbernba.interntask.repository.DisciplineRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/disciplines")
public class DisciplinesController {

	@Autowired
	DisciplineRepository disciplineRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAllDisciplines() {

		List<Discipline> disciplines = disciplineRepository.findBy();
		return new ResponseEntity<>(disciplines, new HttpHeaders(), HttpStatus.OK);
	}
}
