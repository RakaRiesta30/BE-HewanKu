package com.TuBes.HewanKu.Pengguna;

import java.util.HashMap;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.TuBes.HewanKu.KirimEmail;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PenggunaService {
    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private KirimEmail mail;

    @Autowired
    public PenggunaService(PenggunaRepository penggunaRepository) {
        this.penggunaRepository = penggunaRepository;
    }

    public Map<String, Object> register(PenggunaDTO penggunaDTO) {
        Map<String, Object> response = new HashMap<>();
        penggunaRepository.findByEmail(penggunaDTO.getEmail())
                .ifPresentOrElse(ada -> response.put("pesan", "Akun Sudah Tersedia"),
                        () -> {
                            Pengguna pengguna = new Pengguna(
                                    penggunaDTO.getEmail(),
                                    penggunaDTO.getNama(),
                                    penggunaDTO.getNoTelepon(),
                                    passwordEncoder.encode(penggunaDTO.getPassword()));
                            penggunaRepository.save(pengguna);
                            response.put("pesan", "Akun Telah Ditambahkan");
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        penggunaRepository.findByEmail(email)
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
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    pengguna.setOtp(mail.sendEmail(email));
                    penggunaRepository.save(pengguna);
                    if (!pengguna.getOtp().equals("Salah")) {
                        response.put("pesan", "Kode Berhasil dikirim");
                    } else {
                        response.put("pesan", "error");
                    }
                }, () -> response.put("pesan", "email tidak ditemukan"));
        return response;
    }

    public Map<String, Object> verifyOtp(String otp, String email) {
        Map<String, Object> response = new HashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (otp.equals("Salah")) {
                        response.put("pesan", "OTP Salah");
                    } else if (otp.equals(pengguna.getOtp())) {
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
            penggunaRepository.findByEmail(email)
                    .ifPresentOrElse(pengguna -> {
                        pengguna.setPassword(passwordEncoder.encode(password));
                        penggunaRepository.save(pengguna);
                        response.put("pesan", "Password berhasil diganti");
                    }, () -> response.put("pesan", "email tidak ditemukan"));
        }
        return response;
    }
}
