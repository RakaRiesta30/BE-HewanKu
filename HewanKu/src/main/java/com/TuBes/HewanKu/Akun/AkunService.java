// package com.TuBes.HewanKu.Akun;

// import java.util.HashMap;

// import com.TuBes.HewanKu.Akun.Akun;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.Map;

// import com.TuBes.HewanKu.Pengguna.Pengguna;

// import jakarta.transaction.Transactional;

// @Service
// @Transactional
// public class AkunService {

//     private final AkunRepository akunRepository;
//     private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//     @Autowired
//     public AkunService(AkunRepository akunRepository) {
//         this.akunRepository = akunRepository;
//     }

//     public Map<String, Object> register(AkunDTO akunDTO) {
//         Map<String, Object> response = new HashMap<>();
//         akunRepository.findByEmail(akunDTO.getEmail())
//                 .ifPresentOrElse(ada -> response.put("pesan", "Akun Sudah Tersedia"),
//                         () -> {
//                             Akun akun = new Akun(
//                                     akunDTO.getEmail(),
//                                     akunDTO.getNama(),
//                                     passwordEncoder.encode(akunDTO.getPassword()),
//                                     akunDTO.getNoTelepon(),
//                                     akunDTO.getRole());
//                             akunRepository.save(akun);
//                             response.put("pesan", "Akun Telah Ditambahkan");
//                         });
//         return response;
//     }

//     public Map<String, Object> login(String email, String password){
//         Map<String, Object> response = new HashMap<>();
//         akunRepository.findByEmail(email)
//             .ifPresentOrElse(akun -> {
//                 if (passwordEncoder.matches(password, akun.getPassword())){
//                     response.put("pesan", "Password Benar");
//                 }else{
//                     response.put("pesan", "Password atau email salah");
//                 }  
//             }, () -> response.put("pesan", "email tidak ditemukan"));
//             return response;
//     }
// }
