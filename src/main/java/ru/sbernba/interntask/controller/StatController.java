package ru.sbernba.interntask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sbernba.interntask.service.StatService;

@RestController
@RequestMapping(value = "/stat")
public class StatController {

	@Autowired
	StatService statService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> calculateStat() {

		return statService.calculateStat();
	}
}
