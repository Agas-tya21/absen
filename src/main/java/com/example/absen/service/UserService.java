package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.Kantor;
import com.example.absen.model.RoleUser;
import com.example.absen.model.User;
import com.example.absen.repository.KantorRepository;
import com.example.absen.repository.RoleUserRepository;
import com.example.absen.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleUserRepository roleUserRepository;
    
    @Autowired
    private KantorRepository kantorRepository;

    public User createUser(User user) {
        // Find the full RoleUser and Kantor entities or throw an exception
        RoleUser roleUser = Optional.ofNullable(user.getRoleUser())
            .flatMap(r -> roleUserRepository.findById(r.getIdrole()))
            .orElseThrow(() -> new RuntimeException("RoleUser with ID " + user.getRoleUser().getIdrole() + " not found."));

        Kantor kantor = Optional.ofNullable(user.getKantor())
            .flatMap(k -> kantorRepository.findById(k.getIdkantor()))
            .orElseThrow(() -> new RuntimeException("Kantor with ID " + user.getKantor().getIdkantor() + " not found."));

        user.setRoleUser(roleUser);
        user.setKantor(kantor);
        
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
            
            // Perbarui relasi RoleUser dengan pengecekan
            RoleUser roleUser = Optional.ofNullable(userDetails.getRoleUser())
                .flatMap(r -> roleUserRepository.findById(r.getIdrole()))
                .orElseThrow(() -> new RuntimeException("RoleUser with ID " + userDetails.getRoleUser().getIdrole() + " not found."));
            updatedUser.setRoleUser(roleUser);
            
            // Perbarui relasi Kantor dengan pengecekan
            Kantor kantor = Optional.ofNullable(userDetails.getKantor())
                .flatMap(k -> kantorRepository.findById(k.getIdkantor()))
                .orElseThrow(() -> new RuntimeException("Kantor with ID " + userDetails.getKantor().getIdkantor() + " not found."));
            updatedUser.setKantor(kantor);
            
            updatedUser.setFotoselfie(userDetails.getFotoselfie());
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public void deleteUser(String nik) {
        userRepository.deleteById(nik);
    }
}