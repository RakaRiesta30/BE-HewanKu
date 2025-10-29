package com.TuBes.HewanKu.Shelter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.register(shelterDTO);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.login(shelterDTO.getEmail(), shelterDTO.getPassword());
    }
}
