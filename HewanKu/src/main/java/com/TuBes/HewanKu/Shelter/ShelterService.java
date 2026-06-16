package com.TuBes.HewanKu.Shelter;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.FileStorageService;
import com.TuBes.HewanKu.JWTUtil;
import com.TuBes.HewanKu.KirimEmail;
import com.TuBes.HewanKu.TokenBlacklist;
import com.TuBes.HewanKu.TokenBlacklistRepository;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final BaseResponse res;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ShelterAccRepository shelterAccRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    private KirimEmail mail;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public ShelterService(ShelterRepository shelterRepository, BaseResponse res,
            ShelterAccRepository shelterAccRepository, JWTUtil jwtUtil) {
        this.shelterRepository = shelterRepository;
        this.res = res;
        this.shelterAccRepository = shelterAccRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> register(ShelterDTO shelterDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findByEmail(shelterDTO.getEmail())
                .ifPresentOrElse(
                        ada -> response.putAll(
                                res.UNAUTHORIZED("Akun sudah tersedia", null, "Unauthorized, Akun sudah tersedia")),
                        () -> {
                            shelterRepository.findByEmail(shelterDTO.getEmail())
                                    .ifPresentOrElse(
                                            adaa -> response.putAll(res.UNAUTHORIZED("Akun sudah tersedia", null,
                                                    "Unauthorized, Akun sudah tersedia")),
                                            () -> {
                                                if (Objects.equals(shelterDTO.getPassword(),
                                                        shelterDTO.getConfirmPassword())) {
                                                    Shelter shelter = new Shelter(
                                                            shelterDTO.getEmail(),
                                                            shelterDTO.getNamaDepan(),
                                                            shelterDTO.getNamaBelakang(),
                                                            shelterDTO.getNoTelepon(),
                                                            passwordEncoder.encode(shelterDTO.getPassword()));
                                                    shelter.setDisplayName(shelterDTO.getNamaDepan() + " "
                                                            + shelterDTO.getNamaBelakang());
                                                    shelter.setKeyRole(shelterDTO.getKeyRole());
                                                    shelterRepository.save(shelter);
                                                    response.putAll(res.CREATED("Akun telah terbuat", null, null));
                                                } else {
                                                    response.putAll(
                                                            res.UNAUTHORIZED("Konfirmasi password salah", null, null));
                                                }
                                            });
                        });
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new LinkedHashMap<>();

        shelterRepository.findByEmail(email)
                .ifPresentOrElse(shelter -> {
                    if (passwordEncoder.matches(password, shelter.getPassword())) {
                        String token = Jwts.builder()
                                .setSubject(shelter.getId().toString()) // id user
                                .claim("role", shelter.getKeyRole())
                                .claim("email", shelter.getEmail())
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 hari
                                .signWith(jwtUtil.getKey())
                                .compact();
                        Map<String, Object> data = new LinkedHashMap<>();
                        data.put("token", token);
                        data.put("id", shelter.getId());
                        data.put("email", shelter.getEmail());
                        response.putAll(res.OK("Login berhasil", data, null));
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
        shelterRepository.findByEmail(email)
                .ifPresentOrElse(pengguna -> {
                    String otp = (mail.sendEmail(email));
                    pengguna.setOtp(otp);
                    shelterRepository.save(pengguna);
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
        shelterRepository.findByEmail(email)
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
            shelterRepository.findByEmail(email)
                    .ifPresentOrElse(pengguna -> {
                        pengguna.setPassword(passwordEncoder.encode(password));
                        shelterRepository.save(pengguna);
                        response.putAll(res.OK("Password telah diganti", null, null));
                    }, () -> response.putAll(
                            res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        }
        return response;
    }

    public Map<String, Object> createShelter(ShelterAccDTO shelterAccDTO, Long id, MultipartFile file) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    if (shelter.isStatusShelter()) {
                        response.putAll(res.UNAUTHORIZED("Akun sudah membuat shelter", null,
                                "UNAUTHORIZED, Akun sudah membuat shelter"));
                        return;
                    }
                    if (file != null && !file.isEmpty()) {
                        try {
                            String fileName = fileStorageService.uploadFile(file);
                            shelterAccDTO.setUrlLogo(fileName);
                        } catch (IOException e) {
                            response.putAll(res.UNAUTHORIZED("URL logo tidak valid", null,
                                    "UNAUTHORIZED, URL logo tidak valid"));
                            return;
                        }
                    } else {
                        shelterAccDTO.setUrlLogo(null);
                    }
                    ShelterAcc shelterAcc = new ShelterAcc(
                            shelterAccDTO.getAlamatLengkap(),
                            shelterAccDTO.getDeskripsi(),
                            shelterAccDTO.getMetodePembayaran(),
                            shelterAccDTO.getNamaPemilikRekening(),
                            shelterAccDTO.getNamaShelter(),
                            shelterAccDTO.getNomorRekening(),
                            shelterAccDTO.getUrlLogo(),
                            shelterRepository.getReferenceById(id));
                    shelterAccRepository.save(shelterAcc);
                    shelter.setStatusShelter(true);
                    shelterRepository.save(shelter);
                    response.putAll(res.CREATED("Shelter sudah terbuat", null, null));
                },
                        () -> response.putAll(
                                res.UNAUTHORIZED("Shelter tidak ditemukan", null,
                                        "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public Map<String, Object> editPenjual(ShelterDTO shelterDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    mapper.map(shelterDTO, shelter);
                    shelterRepository.save(shelter);
                    response.putAll(res.OK("Akun penjual telah diubah", null, null));
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }

    public Map<String, Object> editShelter(ShelterAccDTO shelterAccDTO, Long id, MultipartFile file) {
        Map<String, Object> response = new LinkedHashMap<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    if (!shelter.isStatusShelter()) {
                        response.putAll(res.UNAUTHORIZED("Akun tidak mempunyai shelter", null,
                                "UNAUTHORIZED, Akun tidak mempunyai shelter"));
                        return;
                    }
                    ShelterAcc shelterAcc = shelter.getShelterAcc();
                    if (file != null && !file.isEmpty()) {
                        try {
                            String fileName = fileStorageService.uploadFile(file);
                            shelterAccDTO.setUrlLogo(fileName);
                        } catch (IOException e) {
                            response.putAll(res.UNAUTHORIZED("URL logo tidak valid", null,
                                    "UNAUTHORIZED, URL logo tidak valid"));
                            return;
                        }
                    } else {
                        shelterAccDTO.setUrlLogo(shelterAcc.getUrlLogo());
                    }
                    mapper.map(shelterAccDTO, shelterAcc);
                    shelterAccRepository.save(shelterAcc);
                    response.putAll(res.OK("Akun shelter telah diubah", null, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null,
                                "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewShelter(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    response.putAll(res.OK("Data shelter dimunculkan", shelter, null));
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewShelterAcc(Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    if (shelter.isStatusShelter()) {
                        response.putAll(res.OK("Data shelter acc dimunculkan", shelter.getShelterAcc(), null));
                    } else {
                        response.putAll(res.FORBIDDEN("Shelter acc tidak ditemukan", null, null));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }

    public Map<String, Object> logout(HttpServletRequest request, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        String headerAuth = request.getHeader("Authorization");
        shelterRepository.findById(id)
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
