package ru.sbernba.interntask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbernba.interntask.model.Rate;

import java.util.ArrayList;

public interface RateRepository extends JpaRepository<Rate, Long> {

	Rate findFirstByRate(Integer rate);
	ArrayList<Rate> findAllBy();
}
