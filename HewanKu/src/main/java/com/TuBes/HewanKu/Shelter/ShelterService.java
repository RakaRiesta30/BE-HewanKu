package com.TuBes.HewanKu.Shelter;

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
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final BaseResponse res;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private KirimEmail mail;

    @Autowired
    public ShelterService(ShelterRepository shelterRepository, BaseResponse res) {
        this.shelterRepository = shelterRepository;
        this.res = res;
    }

    public Map<String, Object> register(ShelterDTO shelterDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findByEmail(shelterDTO.getEmail())
                .ifPresentOrElse(ada -> response.putAll(res.UNAUTHORIZED("Akun sudah tersedia", null, "Unauthorized, Akun sudah tersedia")),
                        () -> {
                            Shelter shelter = new Shelter(
                                    shelterDTO.getEmail(),
                                    shelterDTO.getNama(),
                                    shelterDTO.getNoTelepon(),
                                    passwordEncoder.encode(shelterDTO.getPassword()));
                            shelterRepository.save(shelter);
                            response.putAll(res.CREATED("Akun telah terbuat", null, null));
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findByEmail(email)
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
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(shelter -> {
                    shelter.setOtp(mail.sendEmail(email));
                    shelterRepository.save(shelter);
                    if (!shelter.getOtp().equals("Salah")) {
                        response.putAll(res.OK("Kode berhasil dikirim", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Error", null, "Unauthorized, Error"));
                    }
                }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> verifyOtp(String otp, String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(shelter -> {
                    if (otp.equals("Salah")) {
                        response.putAll(res.UNAUTHORIZED("OTP Salah", null, "Unauthorized, OTP Salah"));
                    } else if (otp.equals(shelter.getOtp())) {
                        response.putAll(res.OK("OTP benar", null, null));
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
            shelterRepository.findByEmail(email)
                    .ifPresentOrElse(shelter -> {
                        shelter.setPassword(passwordEncoder.encode(password));
                        shelterRepository.save(shelter);
                        response.putAll(res.OK("Password telah diganti", null, null));
                    }, () -> response.putAll(res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        }
        return response;
    }
}
