package com.projetSav.PjSav.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetSav.PjSav.dto.AuthRequest;
import com.projetSav.PjSav.dto.AuthResponse;
import com.projetSav.PjSav.exceptions.CompteExisteException;
import com.projetSav.PjSav.jwt.JwtUtil;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.services.ClientServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clients")
public class ClientController {
	@Autowired
	ClientServiceImpl clientService;
	@Autowired 
	AuthenticationManager authentiationManager;
	@Autowired 
	JwtUtil jwtUtil;

	@GetMapping
	public List<Client> getAllClients() {
		return clientService.getAllClients();
	}

	@CrossOrigin
	@GetMapping("/{id}")
	public Client getClientbyId(@PathVariable(value = "id") int idClt) {
		return clientService.getClientbyId(idClt);
	}

	@PostMapping
	public Client createClient(@Valid @RequestBody Client clt) throws CompteExisteException{
		if (clientService.existsByEmail(clt.getEmail())){ 
			throw new CompteExisteException("Cette adresse mail est associé à un compte");
		}
			else return clientService.createClient(clt);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> generateAuthTkn(@RequestBody AuthRequest authRequest) throws Exception{
		try {
			authentiationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getMdp()));
			
		}
		catch(Exception e) {
			throw new Exception("inavalid username/password");
		}
		String jwt = jwtUtil.generateToken(authRequest.getEmail());
		return ResponseEntity.ok(new AuthResponse(jwt));
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
