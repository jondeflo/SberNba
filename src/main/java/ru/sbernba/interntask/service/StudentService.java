package ru.sbernba.interntask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.sbernba.interntask.dto.StudentDto;
import ru.sbernba.interntask.model.*;
import ru.sbernba.interntask.repository.*;
import ru.sbernba.interntask.validation.StudentValidator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Component
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	StudentValidator studentValidator;

	@Autowired
	DisciplineRepository disciplineRepository;

	@Autowired
	RateRepository rateRepository;

	@Autowired
	StudentRateRepository studentRateRepository;

	public ResponseEntity<Object> searchAllStudents() {

		ArrayList<Student> students = studentRepository.findBy();
		return new ResponseEntity<>(students, new HttpHeaders(), HttpStatus.OK);
	}

	private ArrayList<Student> searchStudentByName(Map<String, String> requestData) {

		if (requestData.size() > 1) {
			if (requestData.get("orderby").equals("surname")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllByNameOrderBySurnameAsc(requestData.get("name"));
				} else {
					return studentRepository.findAllByNameOrderBySurnameDesc(requestData.get("name"));
				}
			}
			if (requestData.get("orderby").equals("age")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllByNameOrderByAgeAsc(requestData.get("name"));
				} else {
					return studentRepository.findAllByNameOrderByAgeDesc(requestData.get("name"));
				}
			}
		}
		return studentRepository.findAllByName(requestData.get("name"));
	}

	private ArrayList<Student> searchStudentBySurname(Map<String, String> requestData) {

		if (requestData.size() > 1) {
			if (requestData.get("orderby").equals("name")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllBySurnameOrderByNameAsc(requestData.get("surname"));
				} else {
					return studentRepository.findAllBySurnameOrderByNameDesc(requestData.get("surname"));
				}
			}
			if (requestData.get("orderby").equals("age")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllBySurnameOrderByAgeAsc(requestData.get("surname"));
				} else {
					return studentRepository.findAllBySurnameOrderByAgeDesc(requestData.get("surname"));
				}
			}
		}
		return studentRepository.findAllBySurname(requestData.get("surname"));
	}

	private ArrayList<Student> searchStudentByAge(Map<String, String> requestData) {

		Integer age;

		try {
			age = Integer.parseInt(requestData.get("age"));
		} catch (NumberFormatException e) {
			return null;
		}

		if (requestData.size() > 1) {
			if (requestData.get("orderby").equals("name")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllByAgeOrderByNameAsc(age);
				} else {
					return studentRepository.findAllByAgeOrderByNameDesc(age);
				}
			}
			if (requestData.get("orderby").equals("surname")) {
				if (requestData.get("dir").equals("asc")) {
					return studentRepository.findAllByAgeOrderBySurnameAsc(age);
				} else {
					return studentRepository.findAllByAgeOrderBySurnameDesc(age);
				}
			}
		}
		return studentRepository.findAllByAge(age);
	}

	public ResponseEntity<Object> searchStudentsByParams(Map<String, String> requestData) {

		if (!studentValidator.validateRequestData(requestData))
			return new ResponseEntity<>("Bad request parameters", new HttpHeaders(), HttpStatus.BAD_REQUEST);

		ArrayList<Student> students = null;

		if (requestData.containsKey("name")) {
			students = searchStudentByName(requestData);
		}
		if (requestData.containsKey("surname")) {
			students = searchStudentBySurname(requestData);
		}
		if (requestData.containsKey("age")) {
			students = searchStudentByAge(requestData);
		}

		return new ResponseEntity<>(students, new HttpHeaders(), HttpStatus.OK);
	}

	public ResponseEntity<Object> addStudent(StudentDto studentData) {

		Group group = groupRepository.findFirstByName(studentData.getGroup());
		Student student = new Student();

		student.setName(studentData.getName());
		student.setSurname(studentData.getSurname());
		student.setAge(studentData.getAge());
		student.setGroup(group);

		studentRepository.save(student);
		return new ResponseEntity<>("Student " + studentData.getName() + " " + studentData.getSurname() + " was added", new HttpHeaders(), HttpStatus.OK);
	}

	public ResponseEntity<Object> addRates(Map<String, String> rateData) {

		try {
			Student student = studentRepository.findFirstById(Long.parseLong(rateData.get("student_id")));
			Discipline discipline = disciplineRepository.findFirstByName(rateData.get("discipline"));
			Rate rate = rateRepository.findFirstByRate(Integer.parseInt(rateData.get("rate")));

			StudentRate studentRate = new StudentRate();
			studentRate.setStudent(student);
			studentRate.setDiscipline(discipline);
			studentRate.setRate(rate);
			studentRate.setTimestamp(new Timestamp(System.currentTimeMillis()));

			if (rateData.containsKey("description"))
				studentRate.setDescription(rateData.get("description"));
			else
				studentRate.setDescription(rate.getDescription());

			studentRateRepository.save(studentRate);
		} catch (Exception e) {
			return new ResponseEntity<>("Bad request parameters", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
