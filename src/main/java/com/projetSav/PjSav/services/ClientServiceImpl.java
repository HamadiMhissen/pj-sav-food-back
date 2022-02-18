package com.projetSav.PjSav.services;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.dao.RoleRepository;
import com.projetSav.PjSav.model.Client;
@Service
public class ClientServiceImpl implements ClientService {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Client createClient(Client clt) {
		if (clt.getPassword()!= null)
			clt.setPassword(bCryptPasswordEncoder.encode(clt.getPassword()));
		//"ROLE_USER","ROLE_ADMIN"
		clt.setRoles(new HashSet<>(roleRepository.findAll()));
		clientRepository.save(clt);
		return clt;
	}

	@Override
	public Client updateClient(int idClt, Client nvClt) {
		Client ancienClt = clientRepository.findById(idClt).get();
		ancienClt.setNom(nvClt.getNom());
		ancienClt.setPrenom(nvClt.getPrenom());
		ancienClt.setSexe(nvClt.getSexe());
		ancienClt.setDateNaiss(nvClt.getDateNaiss());
		ancienClt.setEmail(nvClt.getEmail());
		ancienClt.setTel(nvClt.getTel());
		ancienClt.setTelFixe(nvClt.getTelFixe());
		ancienClt.setPassword(nvClt.getPassword());
		ancienClt.setRoles(new HashSet<>(roleRepository.findAll()));
		return clientRepository.save(ancienClt);
	}

	@Override
	public void deleteClient(int idClt) {
		Client clt = clientRepository.findById(idClt).get();
		clientRepository.delete(clt);
	}

	@Override
	public List<Client> getAllClients() {
		List<Client> clients = clientRepository.findAll();
		return clients;
	}
	@Override
	public Client getClientbyId(int id) {
		return clientRepository.findById(id).get();
	}

	@Override
	public Client getClientbyEmail(String email) {
		return clientRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return clientRepository.existsByEmail(email);
	}
	
	

}
