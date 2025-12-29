package com.TuBes.HewanKu.Hewan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animalshelter")
public class HewanController {

    @Autowired
    private HewanService hewanService;

    @GetMapping("/shelter")
    public Map<String, Object> viewHewanShelter(Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.viewHewanShelter(idShelter);
    }

    @GetMapping("/pengguna")
    public Map<String, Object> viewHewanPengguna(Authentication authentication){
        Long idPengguna = (Long) authentication.getPrincipal();
        return hewanService.viewHewanPengguna(idPengguna);
    }

    @GetMapping("/filter")
    public Map<String, Object> filterHewan(Authentication authentication,@RequestBody FilterDTO filterDTO){
        Long idPengguna = (Long) authentication.getPrincipal();
        return hewanService.filterHewan(idPengguna, filterDTO);
    }

    @GetMapping("/{idHewan}")
    public Map<String, Object> detailHewan(Authentication authentication, @PathVariable Long idHewan){
        Long idPengguna = (Long) authentication.getPrincipal();
        return hewanService.detailHewan(idPengguna, idHewan);
    }
}
