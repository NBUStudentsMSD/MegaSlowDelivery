package com.courier.courierapp.controller;

import com.courier.courierapp.dto.ClientDTO;
import com.courier.courierapp.model.Client;
import com.courier.courierapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // GET /api/clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // GET /api/clients/{id}
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    // POST /api/clients
    @PostMapping
    public Client createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }

    // PUT /api/clients/{id}
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(id, clientDTO);
    }

    // DELETE /api/clients/{id}
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}
