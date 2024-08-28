package com.academy.UserRestApi.controller;

import com.academy.UserRestApi.entity.Users;
import com.academy.UserRestApi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping
    public ResponseEntity<List<Users>> listAllUsers(){
        try {
           List<Users> users = service.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable long id){
        try {
            Users users = service.findById(id);
            return ResponseEntity.ok(users);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email){
        try {
          Users user = (Users) service.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Users user){
       try {
           Users users = service.findById(id);
           service.updateUser(id, users);
           return ResponseEntity.ok("User updated successfully.");
       } catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Users> create(@RequestBody Users user){
        try {
            service.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id){
        try {
            service.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/all")
    public ResponseEntity<List<Users>> deleteAllUsers(){
        try {
            List<Users> users = service.deleteAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
