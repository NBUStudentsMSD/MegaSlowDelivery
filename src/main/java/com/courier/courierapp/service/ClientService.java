package com.courier.courierapp.service;

import com.courier.courierapp.dto.ClientDTO;
import com.courier.courierapp.model.Client;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.ClientRepository;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Създаване на клиент
    public Client createClient(ClientDTO clientDTO) {
        // Намираме User от userId
        Users user = usersRepository.findById(clientDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + clientDTO.getUserId()));

        // Създаваме Client обект и го записваме
        Client client = new Client();
        client.setUser(user);

        return clientRepository.save(client);
    }

    // Връщаме всички клиенти
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Връщаме клиент по ID
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
    }

    // Ъпдейт на клиент
    public Client updateClient(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));

        // При желание може да сменим user, ако се подава нов userId
        if (clientDTO.getUserId() != null) {
            Users user = usersRepository.findById(clientDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + clientDTO.getUserId()));
            client.setUser(user);
        }

        return clientRepository.save(client);
    }

    // Изтриване на клиент
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));

        Long userId = client.getUser().getId();

        // Изтриваме първо client
        clientRepository.deleteById(id);

        // Изтриваме свързания users ред
        usersRepository.deleteById(userId);
    }

    public List<Client> getClientsByCompany(Long companyId) {
        return clientRepository.findByCompanyId(companyId);
    }
}
