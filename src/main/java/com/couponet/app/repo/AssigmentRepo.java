package com.couponet.app.repo;

import com.couponet.app.model.Assigment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigmentRepo extends JpaRepository<Assigment, Integer> {
}
