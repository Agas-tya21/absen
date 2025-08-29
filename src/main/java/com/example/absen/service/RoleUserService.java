package com.example.absen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.absen.model.RoleUser;
import com.example.absen.repository.RoleUserRepository;

@Service
public class RoleUserService {

    @Autowired
    private RoleUserRepository roleUserRepository;

    public RoleUser createRoleUser(RoleUser roleUser) {
        return roleUserRepository.save(roleUser);
    }

    public List<RoleUser> getAllRoleUsers() {
        return roleUserRepository.findAll();
    }

    public Optional<RoleUser> getRoleUserById(String idrole) {
        return roleUserRepository.findById(idrole);
    }

    public RoleUser updateRoleUser(String idrole, RoleUser roleUserDetails) {
        Optional<RoleUser> roleUserData = roleUserRepository.findById(idrole);
        if (roleUserData.isPresent()) {
            RoleUser updatedRoleUser = roleUserData.get();
            updatedRoleUser.setNamarole(roleUserDetails.getNamarole());
            return roleUserRepository.save(updatedRoleUser);
        }
        return null;
    }

    public void deleteRoleUser(String idrole) {
        roleUserRepository.deleteById(idrole);
    }
}