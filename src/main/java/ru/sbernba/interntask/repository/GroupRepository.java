package ru.sbernba.interntask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbernba.interntask.model.Group;

import java.util.ArrayList;

public interface GroupRepository extends JpaRepository<Group, Long> {

	ArrayList<Group> findBy();
	Group findFirstByName(String name);
}
