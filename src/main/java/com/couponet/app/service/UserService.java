package com.couponet.app.service;

import com.couponet.app.model.User;
import com.couponet.app.repo.UserRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
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
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            tempUser = userRepo.save(newUser);
        }
        return tempUser;
    }


    public User updateUser(User updateUser){
        User tempUser = null;
        Optional<User> foundUser = userRepo.findById(updateUser.getId());
        if (foundUser.isPresent()){
            tempUser = userRepo.save(updateUser);
        }
        return tempUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }
    }

    public User getLoggedUser(){
        User loggedUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            //noinspection OptionalGetWithoutIsPresent
            Optional<User> foundUser = userRepo.findByEmail(currentUserName);
            if (foundUser.isPresent()){
                loggedUser = foundUser.get();
            }

        }
        return loggedUser;
    }
}