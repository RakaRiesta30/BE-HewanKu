package com.TuBes.HewanKu.Ulasan;
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
@RequestMapping("/ulasan/{idHewan}")
public class UlasanController {
    @Autowired
    private UlasanService ulasanService;

    @PostMapping("/create")
    public Map<String, Object> createUlasan(@RequestBody UlasanDTO ulasanDTO, @PathVariable Long idHewan, Authentication authentication){
        Long idPengguna = (Long) authentication.getPrincipal();
        return ulasanService.createUlasan(ulasanDTO, idHewan, idPengguna);
    }

    @GetMapping("")
    public Map<String, Object> viewUlasan(@PathVariable Long idHewan){
        return ulasanService.viewUlasan(idHewan);
    }
}
