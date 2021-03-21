package com.couponet.app.service;

import com.couponet.app.model.User;
import com.couponet.app.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public User findUserById(int userId){
        User tempUser = null;
        Optional<User> foundUser = userRepo.findById(userId);
        if (foundUser.isPresent()){
            tempUser = foundUser.get();
        }
        return tempUser;
    }

    public boolean deleteUserById(int userId){
        boolean control = false;
        if (userRepo.existsById(userId)){
            userRepo.deleteById(userId);
            control = true;
        }
        return control;
    }

    public User createUser(User newUser){
        User tempUser = null;
        if (!userRepo.existsById(newUser.getId())){
            tempUser = userRepo.save(newUser);
        }
        return tempUser;
    }


    public User updateUser(User updateUser){
        User tempUser = null;
        Optional<User> foundUser = userRepo.findById(updateUser.getId());
        if (foundUser.isPresent()){
            updateUser.setRegistrationDateTime(foundUser.get().getRegistrationDateTime());
            tempUser = userRepo.save(updateUser);
        }
        return tempUser;
    }
}
