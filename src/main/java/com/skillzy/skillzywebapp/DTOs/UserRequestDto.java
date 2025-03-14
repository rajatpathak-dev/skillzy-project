package com.skillzy.skillzywebapp.DTOs;

import com.skillzy.skillzywebapp.Models.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserRequestDto implements Serializable {
    String name;
    String email;
    String password;

    public  User convertToUser(){
        User user = new User();
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        return  user;
    }
}