package com.example.absen.controller;

import java.util.Optional;
import java.util.Collections; // Tambahkan import ini
import java.util.Map; // Tambahkan import ini

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.absen.dto.LoginRequest;
import com.example.absen.model.User;
import com.example.absen.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint untuk proses login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Cari pengguna berdasarkan email di database
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                // Verifikasi password (CATATAN: Ini adalah perbandingan plaintext. 
                // Di aplikasi nyata, gunakan BCryptPasswordEncoder untuk keamanan)
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    // Jika kredensial benar, buat token JWT.
                    String token = "dummy-jwt-token-for-" + user.getNik();
                    return ResponseEntity.ok(Collections.singletonMap("token", token));
                }
            }
            
            // Jika pengguna tidak ditemukan atau password salah
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Email atau kata sandi salah."));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Terjadi kesalahan server saat login."));
        }
    }
}