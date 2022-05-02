package com.projetSav.PjSav.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "jeton_email")
public class JetonEmailConfirm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String jeton;
	
	private LocalDateTime momentCreation;
	
	private LocalDateTime momentExpiration;

	private LocalDateTime momentConfirmation;
	
	@ManyToOne
	private Client clt;
	
	public JetonEmailConfirm(String jetonConfirmation, LocalDateTime momentCreation, LocalDateTime momentExpiration,
			Client clt) {
		this.jeton = jetonConfirmation;
		this.momentCreation = momentCreation;
		this.momentExpiration = momentExpiration;
		this.clt = clt;
	}
	
	
}
