package net.Journal.App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.Journal.App.entity.User;
import net.Journal.App.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userservice;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
       List<User> users =  userservice.getAll();
       if(users != null && !users.isEmpty()){
           return new ResponseEntity<>(users, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user){
        userservice.createAdmin(user);
    }
}
