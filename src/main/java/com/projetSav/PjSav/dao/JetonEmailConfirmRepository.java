package com.projetSav.PjSav.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projetSav.PjSav.model.JetonEmailConfirm;

@Repository
public interface JetonEmailConfirmRepository extends JpaRepository<JetonEmailConfirm, Integer> {

	Optional<JetonEmailConfirm> findByJeton(String jetonConf);

    boolean existsByCltId(int idClt);

    void deleteByCltId(int idClt);
	
	@Transactional
    @Modifying
    @Query("UPDATE JetonEmailConfirm j " +
            "SET j.momentConfirmation = ?2 " +
            "WHERE j.jeton = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
	
}
