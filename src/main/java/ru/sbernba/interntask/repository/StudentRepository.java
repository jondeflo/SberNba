package ru.sbernba.interntask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbernba.interntask.model.Student;

import java.util.ArrayList;

public interface StudentRepository extends JpaRepository<Student, Long> {

	ArrayList<Student> findBy();
	ArrayList<Student> findAllByGroupId(Long id);
	Student findFirstById(Long id);

	ArrayList<Student> findAllByName(String name);
	ArrayList<Student> findAllBySurname(String name);
	ArrayList<Student> findAllByAge(Integer age);

	ArrayList<Student> findAllByNameOrderBySurnameAsc(String name);
	ArrayList<Student> findAllByNameOrderBySurnameDesc(String name);
	ArrayList<Student> findAllByNameOrderByAgeAsc(String name);
	ArrayList<Student> findAllByNameOrderByAgeDesc(String name);

	ArrayList<Student> findAllBySurnameOrderByNameAsc(String name);
	ArrayList<Student> findAllBySurnameOrderByNameDesc(String name);
	ArrayList<Student> findAllBySurnameOrderByAgeAsc(String name);
	ArrayList<Student> findAllBySurnameOrderByAgeDesc(String name);

	ArrayList<Student> findAllByAgeOrderByNameAsc(Integer age);
	ArrayList<Student> findAllByAgeOrderByNameDesc(Integer age);
	ArrayList<Student> findAllByAgeOrderBySurnameAsc(Integer age);
	ArrayList<Student> findAllByAgeOrderBySurnameDesc(Integer age);


}
