package com.courier.courierapp.controller;

import com.courier.courierapp.dto.UserDTO;
import com.courier.courierapp.model.Client;
import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Role;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.ClientRepository;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.UsersRepository;
import com.courier.courierapp.security.JwtUtils;
import com.courier.courierapp.service.UsersService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;

    // Get all users
    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/me")
    public ResponseEntity<Map<String, Object>> getMe(@RequestHeader("Authorization") String token) {

        try {

            token = token.substring(7);
            String username = jwtUtils.getUsernameFromToken(token);
            Users user = usersRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if(user.getRole() == Role.EMPLOYEE) {
                Employee employee = employeeRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Employee not found"));
                Map<String, Object> response = new HashMap<>();
                response.put("company_id", employee.getCompany().getId());
                response.put("office_id", employee.getOffice().getId());
                response.put("username", user.getUsername());
                response.put("user_id", user.getId());
                response.put("role", user.getRole());
                response.put("emp_type", employee.getEmployeeType());


                return ResponseEntity.ok(response);
            }else if(user.getRole() == Role.CLIENT) {
                Map<String, Object> response = new HashMap<>();
                Client client = clientRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Client not found"));
                response.put("company_id", client.getCompany().getId());
                response.put("office_id", null);
                response.put("username", user.getUsername());
                response.put("user_id", user.getId());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            }else {
                Map<String, Object> response = new HashMap<>();
                response.put("company_id", null);
                response.put("office_id", null);
                response.put("username", user.getUsername());
                response.put("user_id", user.getId());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            }


        } catch (Exception e) {
           return ResponseEntity.internalServerError().build();
        }
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create a new employee
    @PostMapping
    public Users createUser(@RequestBody UserDTO user) {
       return userService.createUser(user);
    }

    //Update a user
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
