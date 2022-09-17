package ru.sbernba.interntask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbernba.interntask.model.StudentRate;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface StudentRateRepository extends JpaRepository<StudentRate, Long> {

	ArrayList<StudentRate> findBy();
	ArrayList<StudentRate> findAllByTimestampBetweenOrderByTimestampAsc(Timestamp start, Timestamp end);
	ArrayList<StudentRate> findAllByTimestampBetweenAndDisciplineId(Timestamp start, Timestamp end, Long id);

}
