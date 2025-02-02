package com.courier.courierapp.controller;

import com.courier.courierapp.model.Role;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<Client> getAllClients() {
        // Тук .hasRole("EMPLOYEE") в SecurityConfig,
        // така че ако сме стигнали дотук, значи е EMPLOYEE.
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        // Тук сме .authenticated(), значи може да е CLIENT или EMPLOYEE
        Users currentUser = getCurrentUser();
        Client client = clientService.getClientById(id);

        if (currentUser.getRole() == Role.EMPLOYEE) {
            // EMPLOYEE -> вижда всичко
            return client;
        } else if (currentUser.getRole() == Role.CLIENT) {
            // CLIENT -> може да види само себе си
            if (client.getUser().getId() != currentUser.getId()) {
                throw new RuntimeException("Access denied: You cannot view another client's info");
            }
            return client;
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    // Създаване на нов client - само EMPLOYEE
    @PostMapping
    public Client createClient(@RequestBody ClientDTO clientDTO) {
        // SecurityConfig => .hasRole("EMPLOYEE")
        return clientService.createClient(clientDTO);
    }

    // Ъпдейт на client
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        // SecurityConfig => .authenticated(), може CLIENT или EMPLOYEE
        Users currentUser = getCurrentUser();
        Client client = clientService.getClientById(id);

        if (currentUser.getRole() == Role.EMPLOYEE) {
            // EMPLOYEE -> има право да ъпдейтне всеки
            return clientService.updateClient(id, clientDTO);
        } else if (currentUser.getRole() == Role.CLIENT) {
            // CLIENT -> може да ъпдейтне само ако client.user.id == currentUser.id
            if (client.getUser().getId() != currentUser.getId()) {
                throw new RuntimeException("Access denied: You cannot update another client's info");
            }
            return clientService.updateClient(id, clientDTO);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    // Изтриване на client
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        // SecurityConfig => .authenticated()
        Users currentUser = getCurrentUser();
        Client client = clientService.getClientById(id);

        if (currentUser.getRole() == Role.EMPLOYEE) {
            // EMPLOYEE -> може да трие всеки client
            clientService.deleteClient(id);
        } else if (currentUser.getRole() == Role.CLIENT) {
            // CLIENT -> може да трие само своя
            if (client.getUser().getId() != currentUser.getId()) {
                throw new RuntimeException("Access denied: You cannot delete another client");
            }
            clientService.deleteClient(id);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    private Users getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
    @GetMapping("/company/{companyId}")
    public List<Client> getClientsByCompany(@PathVariable Long companyId) {
        return clientService.getClientsByCompany(companyId);
    }
}

