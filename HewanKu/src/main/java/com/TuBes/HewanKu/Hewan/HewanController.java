package com.TuBes.HewanKu.Hewan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/{id}")
public class HewanController {

    @Autowired
    private HewanService hewanService;

    @PostMapping("/add")
    public Map<String, Object> createHewan(@RequestBody HewanDTO hewanDTO,@PathVariable Long id){
        return hewanService.createHewan(hewanDTO, id);
    }

    @GetMapping("/home")
    public Map<String, Object> viewHewan(@RequestParam String role ,@PathVariable Long id){
        return hewanService.viewHewan(role, id);
    }

    @PostMapping("/edit")
    public Map<String, Object> editHewan(@RequestBody HewanDTO hewanDTO, @PathVariable Long id, @RequestParam Long idHewan){
        return hewanService.editHewan(hewanDTO, id, idHewan);
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteHewan(@PathVariable Long id, @RequestParam Long idHewan){
        return hewanService.deleteHewan(id, idHewan);
    }

    @GetMapping("/adopsi")
    public Map<String, Object> filterHewan(@PathVariable Long id,@RequestBody FilterDTO filterDTO){
        return hewanService.filterHewan(id, filterDTO);
    }

    @GetMapping("")
    public Map<String, Object> detailHewan(@PathVariable Long id, @RequestParam Long idHewan){
        return hewanService.detailHewan(id, idHewan);
    }
}
