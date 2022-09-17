package ru.sbernba.interntask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbernba.interntask.model.Discipline;

import java.util.ArrayList;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

	ArrayList<Discipline> findBy();
	Discipline findFirstByName(String name);
}
