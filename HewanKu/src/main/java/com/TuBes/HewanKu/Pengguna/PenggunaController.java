package com.TuBes.HewanKu.Pengguna;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TuBes.HewanKu.Hewan.HewanService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pengguna")
public class PenggunaController {
    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private HewanService hewanService;

    @PostMapping("/auth/register")
    public Map<String, Object> register(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.register(penggunaDTO);
    }

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.login(penggunaDTO.getEmail(), penggunaDTO.getPassword());
    }

    @PostMapping("/auth/forgot")
    public Map<String, Object> forgotPassword(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.forgotPassword(penggunaDTO.getEmail());
    }

    @PostMapping("/auth/verify")
    public Map<String, Object> verifyOtp(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.verifyOtp(penggunaDTO.getOtp(), penggunaDTO.getEmail());
    }

    @PostMapping("/auth/change")
    public Map<String, Object> changePassword(@RequestBody PenggunaDTO penggunaDTO) {
        return penggunaService.changePassword(penggunaDTO.getPassword(), penggunaDTO.getRepassword(),
                penggunaDTO.getEmail());
    }

    @PutMapping("/editPengguna")
    public Map<String, Object> editPengguna(@RequestBody PenggunaDTO penggunaDTO, Authentication authentication) {
        Long idPengguna = (Long) authentication.getPrincipal();
        return penggunaService.editPengguna(penggunaDTO, idPengguna);
    }

    @GetMapping("/viewPengguna")
    public Map<String, Object> viewPengguna(Authentication authentication) {
        Long idPengguna = (Long) authentication.getPrincipal();
        return penggunaService.viewPengguna(idPengguna);
    }

    @PatchMapping("/addFav/{idHewan}")
    public Map<String, Object> tambahFavorit(Authentication authentication, @PathVariable Long idHewan) {
        Long idPengguna = (Long) authentication.getPrincipal();
        return hewanService.tambahFavorit(idPengguna, idHewan);
    }

    @PatchMapping("/delFav/{idHewan}")
    public Map<String, Object> hapusFavorit(Authentication authentication, @PathVariable Long idHewan) {
        Long idPengguna = (Long) authentication.getPrincipal();
        return hewanService.hapusFavorit(idPengguna, idHewan);
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request, Authentication authentication) {
        Long idPengguna = (Long) authentication.getPrincipal();
        return penggunaService.logout(request, idPengguna);
    }
}
