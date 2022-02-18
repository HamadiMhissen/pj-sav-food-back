package com.projetSav.PjSav.serviceClientTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
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

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.dao.RoleRepository;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.model.Role;
import com.projetSav.PjSav.model.Sexe;
import com.projetSav.PjSav.services.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
	@Mock
	private ClientRepository clientRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
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
		Client clt = new Client(12);
		clt.setNom("hamdi");
		clt.setSexe(Sexe.HOMME);
		clt.setDateNaiss(LocalDate.of(2020, 1, 28));
		clt.setTel("0665457176");
		Mockito.when(clientRepository.save(clt)).thenReturn(clt);
		Client actuel = clientServiceImpl.createClient(clt);
		assertEquals(actuel,clt);
		Mockito.verify(clientRepository).save(clt);

	}

	@Test
	@DisplayName("doit mettre à jour un client avec les nouvelles données")
	void testUpdateClient() {
		Client clt = new Client();

		clt.setNom("hamdi");
		clt.setSexe(Sexe.HOMME);
		clt.setDateNaiss(LocalDate.of(2020, 1, 28));
		clt.setTel("0665457176");
		clt.setPassword(bCryptPasswordEncoder.encode("abcd1234"));
		
		Client cltMaj = new Client(12);
		
		Mockito.when(clientRepository.findById(12)).thenReturn(Optional.of(cltMaj));
		
		cltMaj.setNom(clt.getNom());
		cltMaj.setSexe(clt.getSexe());
		cltMaj.setDateNaiss(clt.getDateNaiss());
		cltMaj.setTel(clt.getTel());
		cltMaj.setPassword(clt.getPassword());
		
		Mockito.when(clientRepository.save(cltMaj)).thenReturn(cltMaj);
		Client actuel = clientServiceImpl.updateClient(12, clt);
		assertEquals(actuel,cltMaj);
		Mockito.verify(clientRepository).save(cltMaj);
	}

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
