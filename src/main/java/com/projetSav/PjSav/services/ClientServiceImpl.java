package com.projetSav.PjSav.services;

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.dao.RoleRepository;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.model.JetonEmailConfirm;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final JetonEmailConfirmService jetonEmailConfirmService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String createClient(Client clt) {
        // "ROLE_USER","ROLE_ADMIN"
        clt.setRoles(new HashSet<>(roleRepository.findAll()));
        clientRepository.save(clt);
        String token = UUID.randomUUID().toString();
        JetonEmailConfirm jeton = new JetonEmailConfirm(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(60), clt);
        jetonEmailConfirmService.createJeton(jeton);
        return token;
    }

    @Override
    public Boolean testerClientValide(String email) {
        return clientRepository.existsByEnabledTrueAndLockedFalseAndEmailEquals(email);
    }

    @Override
    //TODO : refactoring du code, passer au déclaratif
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
        return clientRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    @Override
    public Client getClientbyEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public String encoderMdp(String mdp) {
        return bCryptPasswordEncoder.encode(mdp);

    }

    @Override
    public int enableClient(String username) {
        return clientRepository.enableClient(username);
    }

}
