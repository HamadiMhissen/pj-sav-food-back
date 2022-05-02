package com.projetSav.PjSav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projetSav.PjSav.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	Client findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByEnabledTrueAndLockedFalseAndEmailEquals(String email);

	@Transactional
	@Modifying
	@Query("UPDATE Client c " + "SET c.enabled = TRUE WHERE c.email = ?1")
	int enableClient(String email);
}
