package com.projetSav.PjSav.web;

import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.services.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientServiceImpl clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // @CrossOrigin
    @GetMapping("/{id}")
    public Client getClientbyId(@PathVariable(value = "id") int idClt) {
        return clientService.getClientbyId(idClt);
    }


    @PutMapping("/{id}")
    public Client updateClient(@PathVariable("id") int idClt,
                               @Valid @RequestBody Client nvClt) {
        return clientService.updateClient(idClt, nvClt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") int idClt) {
        clientService.deleteClient(idClt);
        return ResponseEntity
                .ok()
                .body("client portant l'id " + idClt + " supprimé avec succès");
    }

}
