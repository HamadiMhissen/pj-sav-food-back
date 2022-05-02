package com.projetSav.PjSav.dto;

import java.time.LocalDate;
import com.projetSav.PjSav.model.Sexe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.persistence.Column;
import javax.validation.constraints.*;


@AllArgsConstructor
@Getter
public class ClientDto {

	@NotBlank(message = "Nom requis")
	private final String nom;
	@NotBlank(message = "Prenom requis")
	private final String prenom;
	@NotNull(message = "Sexe requis")
	private final Sexe sexe;
	@NotBlank(message = "Email requis")
	@Email
	private final String email;
	private final String password;
	@NotBlank(message = "tel portable requis")
	@Size(min = 10, max = 10, message = "taille de numéro non vailde")
	@Pattern(regexp = "^0[1-9][0-9]*$", message = "format de numéro non valide")
	private final String tel;
	@Pattern(regexp = "^0[1-9][0-9]*$", message = "format de numéro non valide")
	@Size(min = 10, max = 10, message = "taille de numéro non vailde")
	private final String telFixe;
	@NotNull(message = "date de naissance requise")
	@Column(name = "date_naiss")
	private final LocalDate dateNaiss;

}
