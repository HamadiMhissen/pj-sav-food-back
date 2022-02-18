package com.projetSav.PjSav.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	@NotNull(message = "Nom requis")
	@Column
	private String nom;
	@NotNull(message = "Prenom requis")
	@Column
	private String prenom;
	@NotNull(message = "Sexe requis")
	@Enumerated(EnumType.STRING)
	@Column
	private Sexe sexe;
	@NotNull(message = "Email requis")
	@Email
	@Column
	private String email;
	@Column
	private String password;
	@NotNull(message = "tel portable requis")
	@Size(min= 10, max=10, message = "taille de numéro non vailde")
	@Pattern(regexp = "^0[1-9][0-9]*$", message="format de numéro non valide")
	@Column
	private String tel;
	@Pattern(regexp = "^0[1-9][0-9]*$", message="format de numéro non valide")
	@Size(min= 10, max=10, message="taille de numéro non vailde")
	@Column
	private String telFixe;
	//Format date naissance : yyyy-MM-dd
	@NotNull(message = "date de naissance requise")
	@Column(name = "date_naiss")
	private LocalDate dateNaiss;
	//Relation unidirectionnelle
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name="user_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;
	
	public Client() {

	}
	public Client(int id) {
		this.id = id;
	}

}
