package com.projetSav.PjSav.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetSav.PjSav.model.Client;


public interface ClientService {
	Client createClient(Client clt);

	Client updateClient(int idClt, Client nvClt);
	
	Client getClientbyId(int idClt);
	
	Client getClientbyEmail(String email);
	
	boolean existsByEmail(String email);

	void deleteClient(int idClt);

	List<Client> getAllClients();
}
