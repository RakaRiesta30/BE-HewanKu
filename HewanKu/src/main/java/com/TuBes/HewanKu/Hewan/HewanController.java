package com.TuBes.HewanKu.Hewan;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/{id}")
public class HewanController {

    @Autowired
    private HewanService hewanService;

    @PostMapping("/add")
    public Map<String, Object> createHewan(@RequestBody HewanDTO hewanDTO,@PathVariable Long id){
        return hewanService.createHewan(hewanDTO, id);
    }

    @GetMapping("/view")
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
}
