package com.projetSav.PjSav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetSav.PjSav.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
