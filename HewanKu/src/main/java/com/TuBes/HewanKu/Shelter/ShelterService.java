package com.TuBes.HewanKu.Shelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Map<String, Object> register(ShelterDTO shelterDTO) {
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findByEmail(shelterDTO.getEmail())
                .ifPresentOrElse(ada -> response.put("pesan", "Akun Sudah Tersedia"),
                        () -> {
                            Shelter shelter = new Shelter(
                                    shelterDTO.getEmail(),
                                    shelterDTO.getNama(),
                                    shelterDTO.getNoTelepon(),
                                    passwordEncoder.encode(shelterDTO.getPassword()));
                            shelterRepository.save(shelter);
                            response.put("pesan", "Akun Telah Ditambahkan");
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (passwordEncoder.matches(password, pengguna.getPassword())) {
                        response.put("pesan", "Password Benar");
                    } else {
                        response.put("pesan", "Password atau email salah");
                    }
                }, () -> response.put("pesan", "email tidak ditemukan"));
        return response;
    }
}
