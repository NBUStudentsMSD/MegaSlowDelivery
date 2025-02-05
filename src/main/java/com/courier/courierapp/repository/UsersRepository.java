package com.courier.courierapp.repository;

import com.courier.courierapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>
{
    Optional<Users> findByUsername(String username);
}
