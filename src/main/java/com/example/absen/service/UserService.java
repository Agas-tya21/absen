package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.User;
import com.example.absen.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByNik(String nik) {
        return userRepository.findById(nik);
    }

    public User updateUser(String nik, User userDetails) {
        Optional<User> userData = userRepository.findById(nik);
        if (userData.isPresent()) {
            User updatedUser = userData.get();
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setNama(userDetails.getNama());
            updatedUser.setTanggallahir(userDetails.getTanggallahir());
            updatedUser.setNohp(userDetails.getNohp());
            updatedUser.setRoleUser(userDetails.getRoleUser());
            updatedUser.setFotoselfie(userDetails.getFotoselfie());
            updatedUser.setKantor(userDetails.getKantor());
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public void deleteUser(String nik) {
        userRepository.deleteById(nik);
    }
}