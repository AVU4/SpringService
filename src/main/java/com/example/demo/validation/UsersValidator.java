package com.example.demo.validation;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UsersValidator {

    @Autowired
    private UserService userService;

    public boolean validate(Object o) {
        try {
            User user = (User) o;
            if (user.getUsername() == null || user.getPassword() == null) {
                return false;
            }
            User userFromDB = userService.findOneByUsername(user.getUsername());
            if (userFromDB == null){
                return true;
            }else {
                return false;
            }
        }catch (EntityNotFoundException e){
            return true;
        }
    }
}
