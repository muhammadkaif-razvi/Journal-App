package net.Journal.App.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import net.Journal.App.entity.User;
import net.Journal.App.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    public static  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {

            log.error("Error occurred for {} :",user.getUserName(),e);
            return false;
        }

    }
    public void createAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> getAll(){
     return   userRepository.findAll();
    }

}