package com.TuBes.HewanKu.Ulasan;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ulasan/{id}")
public class UlasanController {
    @Autowired
    private UlasanService ulasanService;

    @PostMapping("/{penggunaId}")
    public Map<String, Object> createUlasan(@RequestBody UlasanDTO ulasanDTO, @PathVariable Long id, @PathVariable Long penggunaId){
        return ulasanService.createUlasan(ulasanDTO, id, penggunaId);
    }

    @GetMapping("")
    public Map<String, Object> viewUlasan(@PathVariable Long id){
        return ulasanService.viewUlasan(id);
    }
}
