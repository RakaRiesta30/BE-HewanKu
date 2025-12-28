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
    private final ShelterAccRepository shelterAccRepository;

    @Autowired
    private KirimEmail mail;

    @Autowired
    public ShelterService(ShelterRepository shelterRepository, BaseResponse res,
            ShelterAccRepository shelterAccRepository) {
        this.shelterRepository = shelterRepository;
        this.res = res;
        this.shelterAccRepository = shelterAccRepository;
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
                                                Shelter shelter = new Shelter(
                                                        shelterDTO.getEmail(),
                                                        shelterDTO.getNama(),
                                                        shelterDTO.getNoTelepon(),
                                                        passwordEncoder.encode(shelterDTO.getPassword()));
                                                shelterRepository.save(shelter);
                                                response.putAll(res.CREATED("Akun telah terbuat", null, null));
                                            });
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
                .ifPresentOrElse(shelter -> {
                    shelter.setOtp(mail.sendEmail(email));
                    shelterRepository.save(shelter);
                    if (!shelter.getOtp().equals("Salah")) {
                        response.putAll(res.OK("Kode berhasil dikirim", null, null));
                    } else {
                        response.putAll(res.UNAUTHORIZED("Error", null, "Unauthorized, Error"));
                    }
                }, () -> response.putAll(
                        res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
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
                    .ifPresentOrElse(shelter -> {
                        shelter.setPassword(passwordEncoder.encode(password));
                        shelterRepository.save(shelter);
                        response.putAll(res.OK("Password telah diganti", null, null));
                    }, () -> response.putAll(
                            res.UNAUTHORIZED("Email tidak ditemukan", null, "Unauthorized, Email tidak ditemukan")));
        }
        return response;
    }

    public Map<String, Object> createShelter(ShelterAccDTO shelterAccDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterAccRepository.findByShelter_Id(id)
                .ifPresentOrElse(
                        shelterAcc -> response.putAll(res.UNAUTHORIZED("Akun sudah membuat shelter", null,
                                "UNAUTHORIZED, Akun sudah membuat shelter")),
                        () -> {
                            ShelterAcc shelterAcc = new ShelterAcc(
                                    shelterAccDTO.getEmail(),
                                    shelterAccDTO.getJalan(),
                                    shelterAccDTO.getMetodePembayaran(),
                                    shelterAccDTO.getNamaOwner(),
                                    shelterAccDTO.getNamaShelter(),
                                    shelterAccDTO.getNegaraDaerah(),
                                    shelterAccDTO.getNomorHandphone(),
                                    shelterAccDTO.getZipCode(),
                                    shelterRepository.getReferenceById(id));
                            shelterAccRepository.save(shelterAcc);
                            Shelter shelter = shelterRepository.getByShelterAcc_Id(id);
                            shelter.setStatusShelter(true);
                            shelterRepository.save(shelter);
                            response.putAll(res.CREATED("Shelter sudah terbuat", null, null));
                        });
        return response;
    }

    public Map<String, Object> editPenjual(ShelterDTO shelterDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    shelter.setNama(shelterDTO.getNama());
                    shelter.setEmail(shelterDTO.getEmail());
                    shelter.setNoTelepon(shelterDTO.getNoTelepon());
                    shelterRepository.save(shelter);
                    response.putAll(res.OK("Akun penjual telah diubah", null, null));
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }

    public Map<String, Object> editShelter(ShelterAccDTO shelterAccDTO, Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        shelterAccRepository.findById(id)
                .ifPresentOrElse(shelterAcc -> {
                    shelterAcc.setEmail(shelterAccDTO.getEmail());
                    shelterAcc.setJalan(shelterAccDTO.getJalan());
                    shelterAcc.setMetodePembayaran(shelterAccDTO.getMetodePembayaran());
                    shelterAcc.setNamaOwner(shelterAccDTO.getNamaOwner());
                    shelterAcc.setNamaShelter(shelterAccDTO.getNamaShelter());
                    shelterAcc.setNegaraDaerah(shelterAccDTO.getNegaraDaerah());
                    shelterAcc.setNomorHandphone(shelterAccDTO.getNomorHandphone());
                    shelterAcc.setZipCode(shelterAccDTO.getZipCode());
                    shelterAccRepository.save(shelterAcc);
                    response.putAll(res.OK("Akun shelter telah diubah", null, null));
                }, () -> response
                        .putAll(res.UNAUTHORIZED("Akun tidak ditemukan", null, "UNAUTHORIZED, Akun tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewShelter(Long id){
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                response.putAll(res.OK("Data shelter dimunculkan", shelter, null));
            }, () -> response.putAll(res.UNAUTHORIZED("Shelter tidak ditemukan", null, "UNAUTHORIZED, Shelter tidak ditemukan")));
        return response;
    }
}
