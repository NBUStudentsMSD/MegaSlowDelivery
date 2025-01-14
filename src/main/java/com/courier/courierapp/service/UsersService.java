package com.courier.courierapp.service;

import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    //Get all existing users
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    //Get a user by ID
    public Optional<Users> getUserById(Long id){
        return usersRepository.findById(id);
    }
    //create new user
    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    //update an existing user
    public Users updateUser(Long id, Users updatedUser) {
        return usersRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return usersRepository.save(user);
        }).orElse(null);
    }

    //delete a user
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
