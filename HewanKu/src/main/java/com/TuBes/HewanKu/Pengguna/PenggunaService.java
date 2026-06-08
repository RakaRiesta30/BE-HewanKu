package com.TuBes.HewanKu.Pengguna;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.JWTUtil;
import com.TuBes.HewanKu.KirimEmail;
import com.TuBes.HewanKu.Shelter.ShelterRepository;
import com.TuBes.HewanKu.TokenBlacklist;
import com.TuBes.HewanKu.TokenBlacklistRepository;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PenggunaService {
    private final PenggunaRepository penggunaRepository;
    private final BaseResponse res;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ShelterRepository shelterRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    private KirimEmail mail;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    public PenggunaService(PenggunaRepository penggunaRepository, BaseResponse res,
            ShelterRepository shelterRepository, JWTUtil jwtUtil) {
        this.penggunaRepository = penggunaRepository;
        this.res = res;
        this.shelterRepository = shelterRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> register(PenggunaDTO penggunaDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findByEmail(penggunaDTO.getEmail())
                .ifPresentOrElse(
                        ada -> response.putAll(
                                res.UNAUTHORIZED("Akun sudah tersedia", null, "Unauthorized, Akun sudah tersedia")),
                        () -> {
                            penggunaRepository.findByEmail(penggunaDTO.getEmail())
                                    .ifPresentOrElse(
                                            adaa -> response.putAll(res.UNAUTHORIZED("Akun sudah tersedia", null,
                                                    "Unauthorized, Akun sudah tersedia")),
                                            () -> {
                                                Pengguna pengguna = new Pengguna(
                                                        penggunaDTO.getNamaDepan(),
                                                        penggunaDTO.getNamaBelakang(),
                                                        penggunaDTO.getNoTelepon(),
                                                        penggunaDTO.getEmail(),
                                                        passwordEncoder.encode(penggunaDTO.getPassword()));
                                                pengguna.setDisplayName(penggunaDTO.getNamaDepan() + " "
                                                        + penggunaDTO.getNamaBelakang());
                                                if (Objects.equals(penggunaDTO.getConfirmPassword(),
                                                        penggunaDTO.getPassword())) {
                                                    penggunaRepository.save(pengguna);
                                                    response.putAll(res.CREATED("Akun telah terbuat", null, null));
                                                } else {
                                                    response.putAll(
                                                            res.FORBIDDEN("Konfirmasi password salah atau belum diisi",
                                                                    null, null));
                                                }
                                            });
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (passwordEncoder.matches(password, pengguna.getPassword())) {
                        String token = Jwts.builder()
                                .setSubject(pengguna.getId().toString()) // id user
                                .claim("role", "PENGGUNA")
                                .claim("email", pengguna.getEmail())
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 hari
                                .signWith(jwtUtil.getKey())
                                .compact();
                        Map<String, Object> data = new LinkedHashMap<>();
                        data.put("token", token);
                        data.put("id", pengguna.getId());
                        data.put("email", pengguna.getEmail());
                        response.putAll(res.OK("Password benar", data, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Email atau password salah", null,
                                "Unauthorized, Email atau password salah"));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> forgotPassword(String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    String otp = (mail.sendEmail(email));
                    pengguna.setOtp(otp);
                    penggunaRepository.save(pengguna);
                    if (otp.length() == 6) {
                        response.putAll(res.OK("Kode berhasil dikirim", otp, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Error", otp, "Unauthorized, Error"));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        return response;
    }

    public Map<String, Object> verifyOtp(String otp, String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    if (otp.equals("Salah")) {
                        response.putAll(res.UNAUTHORIZED("OTP Salah", null, "Unauthorized, OTP Salah"));
                    } else if (otp.equals(pengguna.getOtp())) {
                        response.putAll(res.OK("OTP benar", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("OTP Salah", null, "Unauthorized, OTP Salah"));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
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
                    }, () -> response.putAll(
                            res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        }
        return response;
    }

    public Map<String, Object> editPengguna(PenggunaDTO penggunaDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    pengguna.setDisplayName(penggunaDTO.getDisplayName());
                    pengguna.setUsername(penggunaDTO.getUsername());
                    pengguna.setEmail(penggunaDTO.getEmail());
                    pengguna.setNoTelepon(penggunaDTO.getNoTelepon());
                    pengguna.setNegaraDaerah(penggunaDTO.getNegaraDaerah());
                    pengguna.setJalan(penggunaDTO.getJalan());
                    pengguna.setZipCode(penggunaDTO.getZipCode());
                    penggunaRepository.save(pengguna);
                    response.putAll(res.OK("Akun pengguna telah diubah", null, null));
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewPengguna(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    response.putAll(res.OK("Pengguna ditemukan", pengguna, null));
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }

    public Map<String, Object> logout(HttpServletRequest request, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        String headerAuth = request.getHeader("Authorization");
        penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                        String token = headerAuth.substring(7);

                        TokenBlacklist tokenMasukDaftarHitam = new TokenBlacklist();
                        tokenMasukDaftarHitam.setToken(token);
                        tokenBlacklistRepository.save(tokenMasukDaftarHitam);

                        response.putAll(res.OK("Berhasil logout", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Gagal logout", null, null));
                    }
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }
}
