package com.courier.courierapp.security;

import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Търсим в базата (чрез UsersRepository) дали имаме такъв user
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Връщаме Spring Security обект (UserDetails)
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // вече хеширана в базата
                .roles(user.getRole().name()) // "EMPLOYEE" или "CLIENT"
                .build();
    }
}
