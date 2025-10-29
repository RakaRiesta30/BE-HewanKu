// package com.TuBes.HewanKu.Akun;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.TuBes.HewanKu.Akun.AkunService;

// @RestController
// public class AkunController {
//     @Autowired
//     private AkunService akunService;

//     @PostMapping("/register")
//     public Map<String, Object> register(@RequestBody AkunDTO akunDTO) {
//         return akunService.register(akunDTO);
//     }

//     @PostMapping("/login")
//     public Map<String, Object> login(@RequestBody AkunDTO akunDTO){
//         return akunService.login(akunDTO.getEmail(),akunDTO.getPassword());
//     }
// }
