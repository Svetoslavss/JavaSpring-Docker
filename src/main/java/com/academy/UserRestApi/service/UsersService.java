package com.academy.UserRestApi.service;

import com.academy.UserRestApi.entity.Users;
import com.academy.UserRestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UsersService {

    @Autowired
    private UserRepository repository;

    public List<Users> getAllUsers(){
        return repository.findAll();
    }

    public Users create(Users user){
        return repository.save(user);
    }

    public Users findById(long id){
        return repository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("User not found with this ID" + id));
    }

    public List<Users> updateUser(Long id, Users users){
        Users existingUser = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setUsername(users.getUsername());
        existingUser.setPassword(users.getPassword());
        existingUser.setEmail(users.getEmail());
        return Stream.of(repository.save(existingUser))
               .collect(Collectors.toList());
    }

    public boolean deleteById(long id){
        boolean existing = repository.existsById(id);
        if (existing){
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Users> deleteAll(){
        List<Users> deleteUsers = repository.findAll();
        repository.deleteAll();
        return deleteUsers;
    }

    public List<Users> findByUsername(String username){
        return repository.findByUsername(username);
    }

    public List<Users> findByEmail(String email){
        List<Users> user = repository.findUserByEmail(email)
                .stream().filter(e -> e.getEmail().equals(email))
                .toList();
                if (!user.isEmpty()){
                    return user;
                } else {
                    throw new NoSuchElementException("User with this email does not exist.");
                }
    }


}
