package com.projetSav.PjSav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetSav.PjSav.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	 Client findByEmail(String email);
	 boolean existsByEmail(String email);
}
