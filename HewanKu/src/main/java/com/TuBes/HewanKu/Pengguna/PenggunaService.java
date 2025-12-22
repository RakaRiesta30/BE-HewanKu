package com.TuBes.HewanKu.Pengguna;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.KirimEmail;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PenggunaService {
    private final PenggunaRepository penggunaRepository;
    private final BaseResponse res;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private KirimEmail mail;

    @Autowired
    public PenggunaService(PenggunaRepository penggunaRepository, BaseResponse res) {
        this.penggunaRepository = penggunaRepository;
        this.res = res;
    }
    
    public Map<String, Object> register(PenggunaDTO penggunaDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(penggunaDTO.getEmail())
                .ifPresentOrElse(ada -> response.putAll(res.UNAUTHORIZED("Akun sudah tersedia", null, "Unauthorized, Akun sudah tersedia")),
                        () -> {
                            Pengguna pengguna = new Pengguna(
                                    penggunaDTO.getEmail(),
                                    penggunaDTO.getNama(),
                                    penggunaDTO.getNoTelepon(),
                                    passwordEncoder.encode(penggunaDTO.getPassword()));
                            penggunaRepository.save(pengguna);
                            response.putAll(res.CREATED("Akun telah terbuat", null, null));
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (passwordEncoder.matches(password, pengguna.getPassword())) {
                        response.putAll(res.OK("Password benar", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Email atau password salah", null, "Unauthorized, Email atau password salah"));
                    }
                }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> forgotPassword(String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    pengguna.setOtp(mail.sendEmail(email));
                    penggunaRepository.save(pengguna);
                    if (!pengguna.getOtp().equals("Salah")) {
                        response.putAll(res.OK("Kode berhasil dikirim", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Error", null, "Unauthorized, Error"));
                    }
                }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> verifyOtp(String otp, String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (otp.equals("Salah")) {
                        response.putAll(res.UNAUTHORIZED("OTP Salah", null, "Unauthorized, OTP Salah"));
                    } else if (otp.equals(pengguna.getOtp())) {
                        response.putAll(res.OK("OTP benar", null, null));response.putAll(res.OK("OTP benar", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("OTP Salah", null, "Unauthorized, OTP Salah"));
                    }
                }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> changePassword(String password, String repassword, String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        if (!password.equals(repassword)) {
            response.putAll(res.UNAUTHORIZED("Masukkan ulang password", null, "Unauthorized, Masukkan ulang password"));
        } else {
            penggunaRepository.findByEmail(email)
                    .ifPresentOrElse(pengguna -> {
                        pengguna.setPassword(passwordEncoder.encode(password));
                        penggunaRepository.save(pengguna);
                        response.putAll(res.OK("Password telah diganti", null, null));
                    }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        }
        return response;
    }
}
