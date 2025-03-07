package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {



    Optional<User> findByEmail(String email);


}
