package com.skillzy.skillzywebapp.Repositories;

import com.skillzy.skillzywebapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);


}
