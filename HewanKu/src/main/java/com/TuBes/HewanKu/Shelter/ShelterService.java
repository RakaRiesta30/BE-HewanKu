package com.TuBes.HewanKu.Shelter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.KirimEmail;

import java.util.HashMap;
import java.util.Map;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private KirimEmail mail;

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

    public Map<String, Object> forgotPassword(String email) {
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(shelter -> {
                    shelter.setOtp(mail.sendEmail(email));
                    shelterRepository.save(shelter);
                    if (!shelter.getOtp().equals("Salah")) {
                        response.put("pesan", "Kode Berhasil dikirim");
                    } else {
                        response.put("pesan", "error");
                    }
                }, () -> response.put("pesan", "email tidak ditemukan"));
        return response;
    }

    public Map<String, Object> verifyOtp(String otp, String email) {
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(shelter -> {
                    if (otp.equals("Salah")) {
                        response.put("pesan", "OTP Salah");
                    } else if (otp.equals(shelter.getOtp())) {
                        response.put("pesan", "OTP Benar");
                    } else {
                        response.put("pesan", "OTP Salah");
                    }
                }, () -> response.put("pesan", "email tidak ditemukan"));
        return response;
    }

    public Map<String, Object> changePassword(String password, String repassword, String email) {
        Map<String, Object> response = new HashMap<>();
        if (!password.equals(repassword)) {
            response.put("pesan", "Masukkan ulang password");
        } else {
            shelterRepository.findByEmail(email)
                    .ifPresentOrElse(shelter -> {
                        shelter.setPassword(passwordEncoder.encode(password));
                        shelterRepository.save(shelter);
                        response.put("pesan", "Password berhasil diganti");
                    }, () -> response.put("pesan", "email tidak ditemukan"));
        }
        return response;
    }
}
