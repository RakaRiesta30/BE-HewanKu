package com.TuBes.HewanKu.Pesanan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pesanan")
public class PesananController {
    @Autowired
    private PesananService pesananService;

    @PostMapping("/{idHewan}/create")
    public Map<String, Object> createPesanan(@PathVariable Long idHewan, Authentication authentication){
        Long idPengguna = (Long) authentication.getPrincipal();
        return pesananService.createPesanan(idHewan, idPengguna);
    }

    @GetMapping("/pengguna/view")
    public Map<String, Object> viewPesananPengguna(Authentication authentication){
        Long idPengguna = (Long) authentication.getPrincipal();
        return pesananService.viewPesananPengguna(idPengguna);
    }

    @GetMapping("/shelter/view")
    public Map<String, Object> viewPesananShelter(Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return pesananService.viewPesananShelter(idShelter);
    }

    @PostMapping("/{id}/fill")
    public Map<String, Object> isiForm(@RequestBody FormDTO formDTO, @PathVariable Long id, Authentication authentication){
        Long idPengguna = (Long) authentication.getPrincipal();
        return pesananService.isiForm(formDTO, id, idPengguna);
    }

    @PostMapping("/{id}/confirm")
    public Map<String, Object> confirmForm(@RequestBody PesananDTO pesananDTO, @PathVariable Long id, Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return pesananService.confirmForm(pesananDTO, id, idShelter);
    }
}
