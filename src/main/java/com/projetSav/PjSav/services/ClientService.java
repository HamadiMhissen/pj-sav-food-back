package com.projetSav.PjSav.services;

import java.util.List;

import com.projetSav.PjSav.model.Client;


public interface ClientService {
	String createClient(Client clt);

	String encoderMdp(String mdp);

	Boolean testerClientValide(String email);

	Client updateClient(int idClt, Client nvClt);
	
	Client getClientbyId(int idClt);
	
	Client getClientbyEmail(String email);
	
	boolean existsByEmail(String email);

	void deleteClient(int idClt);

	List<Client> getAllClients();

	int enableClient(String username);
	
	
	
	
}
