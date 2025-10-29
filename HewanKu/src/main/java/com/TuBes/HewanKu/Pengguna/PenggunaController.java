package com.TuBes.HewanKu.Pengguna;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pengguna")
public class PenggunaController {
    @Autowired
    private PenggunaService penggunaService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.register(penggunaDTO);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.login(penggunaDTO.getEmail(), penggunaDTO.getPassword());
    }

    @PostMapping("/forgot")
    public Map<String, Object> forgotPassword(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.forgotPassword(penggunaDTO.getEmail());
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyOtp(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.verifyOtp(penggunaDTO.getOtp(), penggunaDTO.getEmail());
    }

    @PostMapping("/change")
    public Map<String, Object> changePassword(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.changePassword(penggunaDTO.getPassword(), penggunaDTO.getRepassword(),
                penggunaDTO.getEmail());
    }
}
