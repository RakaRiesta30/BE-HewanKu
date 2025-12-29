package com.TuBes.HewanKu.Shelter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TuBes.HewanKu.Hewan.HewanDTO;
import com.TuBes.HewanKu.Hewan.HewanService;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterRepository shelterRepository;
    
    @Autowired
    private ShelterService shelterService;
    private HewanService hewanService;

    public ShelterController(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.register(shelterDTO);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.login(shelterDTO.getEmail(), shelterDTO.getPassword());
    }

    @PostMapping("/forgot")
    public Map<String, Object> forgotPassword(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.forgotPassword(shelterDTO.getEmail());
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyOtp(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.verifyOtp(shelterDTO.getOtp(), shelterDTO.getEmail());
    }

    @PostMapping("/change")
    public Map<String, Object> changePassword(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.changePassword(shelterDTO.getPassword(), shelterDTO.getRepassword(),
                shelterDTO.getEmail());
    }

    @PostMapping("/create") 
    public Map<String, Object> createShelter(@RequestBody ShelterAccDTO shelterAccDTO, Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.createShelter(shelterAccDTO, idShelter);
    }

    @PostMapping("/editPenjual") 
    public Map<String, Object> editPenjual(@RequestBody ShelterDTO shelterDTO, Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.editPenjual(shelterDTO, idShelter);
    }

    @PostMapping("/editShelter") 
    public Map<String, Object> editShelter(@RequestBody ShelterAccDTO shelterAccDTO, Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        ShelterAcc shelterAcc = shelterRepository.getShelterAccById(idShelter);
        return shelterService.editShelter(shelterAccDTO, shelterAcc.getId());
    }

    @GetMapping("/view")
    public Map<String, Object> viewShelter(Authentication authentication){
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.viewShelter(idShelter);
    }

    @DeleteMapping("/delete/{idHewan}")
    public Map<String, Object> deleteHewan(Authentication authentication, @PathVariable Long idHewan) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.deleteHewan(idShelter, idHewan);
    }

    @PostMapping("/add")
    public Map<String, Object> createHewan(@RequestBody HewanDTO hewanDTO, Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.createHewan(hewanDTO, idShelter);
    }

    @PostMapping("/edit/{idHewan}")
    public Map<String, Object> editHewan(@RequestBody HewanDTO hewanDTO, Authentication authentication,
            @PathVariable Long idHewan) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.editHewan(hewanDTO, idShelter, idHewan);
    }
}
