package com.projetSav.PjSav.serviceClientTest;

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.dao.JetonEmailConfirmRepository;
import com.projetSav.PjSav.dao.RoleRepository;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.model.Sexe;
import com.projetSav.PjSav.services.ClientServiceImpl;
import com.projetSav.PjSav.services.JetonEmailConfirmService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private JetonEmailConfirmRepository jetonRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private JetonEmailConfirmService jetonEmailConfirmService;
    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    @DisplayName("le client persisté doit être le même que le crée")
    void testCreateClient() {
        String mdpCryptée = clientServiceImpl.encoderMdp("rgerge125!.}Abc");
        Client clt = Client.builder()
                .id(12)
                .nom("Elwerghi")
                .prenom("hamdi")
                .sexe(Sexe.HOMME)
                .dateNaiss(LocalDate.of(2020, 1, 28))
                .tel("0665457176")
                .password(mdpCryptée)
                .build();
        Mockito.when(clientRepository.save(clt)).thenReturn(clt);
        String actuel = clientServiceImpl.createClient(clt);
        //assertEquals(actuel,clt);
        Mockito.verify(clientRepository).save(clt);

    }

//    @Test
//    @DisplayName("doit mettre à jour un client avec les nouvelles données")
//    void testUpdateClient() {
//        Client clt = Client.builder()
//                .id(12)
//                .nom("Elwerghi")
//                .prenom("hamdi")
//                .sexe(Sexe.HOMME)
//                .dateNaiss(LocalDate.of(2020, 1, 28))
//                .tel("0665457176")
//                .password(bCryptPasswordEncoder.encode("abcd1234!"))
//                .build();
//        Mockito.when(clientRepository.findById(12)).thenReturn(Optional.ofNullable(clt));
//
//        Client cltAjour = Client.builder()
//                .id(clt.getId())
//                .nom(clt.getNom())
//                .prenom(clt.getPrenom())
//                .sexe(clt.getSexe())
//                .dateNaiss(clt.getDateNaiss())
//                .email(clt.getEmail())
//                .tel(clt.getTel())
//                .telFixe(clt.getTelFixe())
//                .password(clt.getPassword())
//                .roles(new HashSet<>(roleRepository.findAll()))
//                .build();
//
//        Mockito.when(clientRepository.save(cltAjour)).thenReturn(cltAjour);
//
//        Client actuel = clientServiceImpl.updateClient(12, clt);
//        assertEquals(actuel, cltAjour);
//        Mockito.verify(clientRepository).save(cltAjour);
//    }

    @Test
    @DisplayName("doit supprimer un client étant donné son identifiant")
    void testDeleteClient() {
        Client clt = new Client(12);
        Mockito.when(clientRepository.findById(12)).thenReturn(Optional.of(clt));
        clientServiceImpl.deleteClient(12);
        Mockito.verify(clientRepository).delete(clt);
    }

    @Test
    @DisplayName("doit récupérer tous les clients")
    void testGetAllClients() {
        Client clt = new Client();
        List<Client> expected = new ArrayList<Client>();
        expected.add(clt);
        expected.add(clt);
        expected.add(clt);
        Mockito.when(clientRepository.findAll()).thenReturn(expected);
        List<Client> actuel = clientServiceImpl.getAllClients();
        assertEquals(actuel, expected);
        assertEquals(actuel.size(), 3);
        Mockito.verify(clientRepository).findAll();
    }

    @Test
    @DisplayName("doit récupérer un client à partir de son id")
    void testGetClientbyId() {
        Optional<Client> expected = Optional.of(new Client(12));
        Mockito.when(clientRepository.findById(12)).thenReturn(expected);
        Client actuel = clientServiceImpl.getClientbyId(12);
        assertThat(actuel).isSameAs(expected.get());
        Mockito.verify(clientRepository).findById(12);
    }

    @Test
    @DisplayName("doit récupérer un client à partir de son email")
    void testGetClientbyEmail() {
        String email = "hlif@gmail.com";
        Client expected = new Client();
        expected.setEmail(email);
        Mockito.when(clientRepository.findByEmail(email)).thenReturn(expected);
        Client actuel = clientServiceImpl.getClientbyEmail(email);
        assertThat(actuel).isSameAs(expected);
        Mockito.verify(clientRepository).findByEmail(email);
    }

    @Test
    @DisplayName("doit retourner vrai si l'email est associé déjà à un compte")
    void testExistsByEmail() {
        String email = "hlif@gmail.com";
        Mockito.when(clientRepository.existsByEmail(email)).thenReturn(true);
        boolean actuel = clientServiceImpl.existsByEmail(email);
        Mockito.verify(clientRepository).existsByEmail(email);
        assertTrue(actuel);
    }

}
