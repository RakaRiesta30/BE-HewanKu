package com.TuBes.HewanKu.Hewan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/{id}")
public class HewanController {

    @Autowired
    private HewanService hewanService;

    @GetMapping("")
    public Map<String, Object> viewHewan(@RequestParam String role ,@PathVariable Long id){
        return hewanService.viewHewan(role, id);
    }

    @GetMapping("/filter")
    public Map<String, Object> filterHewan(@PathVariable Long id,@RequestBody FilterDTO filterDTO){
        return hewanService.filterHewan(id, filterDTO);
    }

    @GetMapping("/{idHewan}")
    public Map<String, Object> detailHewan(@PathVariable Long id, @PathVariable Long idHewan){
        return hewanService.detailHewan(id, idHewan);
    }
}
