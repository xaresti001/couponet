package com.couponet.app.repo;

import com.couponet.app.model.ClientOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrgRepo extends JpaRepository<ClientOrg, Integer> {
}
