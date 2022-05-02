package com.projetSav.PjSav.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import com.projetSav.PjSav.dto.ClientDto;
import com.projetSav.PjSav.services.InscriptionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.projetSav.PjSav.dto.LoginRequest;
import com.projetSav.PjSav.exceptions.CompteExisteException;
import com.projetSav.PjSav.jwt.JwtUtil;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.services.ClientServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientServiceImpl clientService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final InscriptionService inscriptionService;

	public ClientController(ClientServiceImpl service, AuthenticationManager authManager, JwtUtil jwtUtil, InscriptionService inscriptionService) {
		this.clientService = service;
		this.authenticationManager = authManager;
		this.jwtUtil = jwtUtil;
		this.inscriptionService = inscriptionService;
	}

	@GetMapping
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}

	// @CrossOrigin
	@GetMapping("/{id}")
	public Client getClientbyId(@PathVariable(value = "id") int idClt) {
		return clientService.getClientbyId(idClt);
	}

	@GetMapping("/authentication/sign-up/confirm")
	public String confirmClientEmail(@RequestParam("token") String token) {
		return inscriptionService.confirmerJeton(token);
	}

	@PostMapping("/authentication/sign-up")
	public String createClient(@Valid @RequestBody ClientDto cltRequest) throws CompteExisteException {
		if (clientService.existsByEmail(cltRequest.getEmail()))
			throw new CompteExisteException("Cette adresse mail est associé à un compte");
		return inscriptionService.inscrireClient(cltRequest);
	}

	@PostMapping("/authentication/login")
	public ResponseEntity<String> generateAuthTkn(@RequestBody LoginRequest authRequest) throws Exception {
		if (!clientService.testerClientValide(authRequest.getEmail()))
			return ResponseEntity.ok("Le client n'a pas assez de privilèges pour se connecter");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getMdp()));
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		HttpHeaders headerResponse = new HttpHeaders();
		String jwt = jwtUtil.generateToken(authRequest.getEmail(), authorities);

		headerResponse.add(jwtUtil.getHttpHeader(), jwtUtil.getTokenPrefix() + jwt);
		return ResponseEntity.ok().headers(headerResponse).body(jwt);
	}

	@PutMapping("/{id}")
	public Client updateClient(@PathVariable("id") int idClt, @Valid @RequestBody Client nvClt) {
		return clientService.updateClient(idClt, nvClt);
	}

	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable("id") int idClt) {
		clientService.deleteClient(idClt);
	}

}
