package com.projetSav.PjSav.web;

import com.projetSav.PjSav.dto.ClientDto;
import com.projetSav.PjSav.dto.LoginRequest;
import com.projetSav.PjSav.exceptions.CompteExisteException;
import com.projetSav.PjSav.jwt.JwtUtil;
import com.projetSav.PjSav.services.ClientServiceImpl;
import com.projetSav.PjSav.services.InscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")//pj-sav-food/sign-up-mdp
@RestController
@RequestMapping("/api/clients/authentication")
@AllArgsConstructor
public class SouscriptionController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final InscriptionService inscriptionService;
    private final ClientServiceImpl clientService;

    @GetMapping("/sign-up/confirm")
    public String confirmClientEmail(@RequestParam("token") String token) {
        return inscriptionService.confirmerJeton(token);
    }

    @PostMapping("/sign-up")
    public String createClient(@Valid @RequestBody ClientDto cltRequest) throws CompteExisteException {
        if (clientService.existsByEmail(cltRequest.getEmail()))
            throw new CompteExisteException("Cette adresse mail est associé à un compte");
        return inscriptionService.inscrireClient(cltRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> generateAuthTkn(@RequestBody LoginRequest authRequest) throws Exception {
        if (!clientService.existsByEmail(authRequest.getEmail()))
            return ResponseEntity.badRequest().body("adresse mail erronée, compte inexistant !");
        if (!clientService.testerClientActivé(authRequest.getEmail()))
            return ResponseEntity.badRequest().body("Vous n'avez pas assez de privilèges pour se connecter, Veuillez confirmez votre email !");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getMdp()));
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        HttpHeaders headerResponse = new HttpHeaders();
        String jwt = jwtUtil.generateToken(authRequest.getEmail(), authorities);

        headerResponse.add(jwtUtil.getHttpHeader(), jwtUtil.getTokenPrefix() + jwt);
        return ResponseEntity.ok().headers(headerResponse).body(jwt);
    }
}

