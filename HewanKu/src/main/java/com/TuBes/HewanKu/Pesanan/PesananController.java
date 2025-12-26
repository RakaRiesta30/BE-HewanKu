package com.TuBes.HewanKu.Pesanan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pesanan")
public class PesananController {
    @Autowired
    private PesananService pesananService;

    @PostMapping("/{idHewan}/{idPengguna}")
    public Map<String, Object> createPesanan(@PathVariable Long idHewan, @PathVariable Long idPengguna){
        return pesananService.createPesanan(idHewan, idPengguna);
    }

    @GetMapping("/{id}/view")
    public Map<String, Object> viewPesanan(@PathVariable Long id, @RequestParam String role){
        if ("pengguna".equals(role)){
            return pesananService.viewPesananPengguna(id);
        }
        return pesananService.viewPesananShelter(id);
    }

    @PostMapping("/{idPengguna}/{id}/")
    public Map<String, Object> isiForm(@RequestBody PesananDTO pesananDTO, @PathVariable Long id, @PathVariable Long idPengguna){
        return pesananService.isiForm(pesananDTO, id, idPengguna);
    }

    @PostMapping("/{idShelter}/{id}/confirm")
    public Map<String, Object> confirmForm(@RequestBody PesananDTO pesananDTO, @PathVariable Long id, @PathVariable Long idShelter){
        return pesananService.confirmForm(pesananDTO, id, idShelter);
    }
}
