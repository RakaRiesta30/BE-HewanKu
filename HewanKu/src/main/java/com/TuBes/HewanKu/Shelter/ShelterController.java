package com.TuBes.HewanKu.Shelter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ShelterService shelterService;
    private HewanService hewanService;

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

    @PostMapping("/create/{idPenjual}") 
    public Map<String, Object> createShelter(@RequestBody ShelterAccDTO shelterAccDTO, @PathVariable Long idPenjual){
        return shelterService.createShelter(shelterAccDTO, idPenjual);
    }

    @PostMapping("/editPenjual/{idPenjual}") 
    public Map<String, Object> editPenjual(@RequestBody ShelterDTO shelterDTO, @PathVariable Long idPenjual){
        return shelterService.editPenjual(shelterDTO, idPenjual);
    }

    @PostMapping("/editShelter/{idShelter}") 
    public Map<String, Object> editShelter(@RequestBody ShelterAccDTO shelterAccDTO, @PathVariable Long idShelter){
        return shelterService.editShelter(shelterAccDTO, idShelter);
    }

    @GetMapping("/view/{idShelter}")
    public Map<String, Object> viewShelter(@PathVariable Long idShelter){
        return shelterService.viewShelter(idShelter);
    }

    @DeleteMapping("/delete/{id}/{idHewan}")
    public Map<String, Object> deleteHewan(@PathVariable Long id, @PathVariable Long idHewan) {
        return hewanService.deleteHewan(id, idHewan);
    }

    @PostMapping("/add/{id}")
    public Map<String, Object> createHewan(@RequestBody HewanDTO hewanDTO, @PathVariable Long id) {
        return hewanService.createHewan(hewanDTO, id);
    }

    @PostMapping("/edit/{id}/{idHewan}")
    public Map<String, Object> editHewan(@RequestBody HewanDTO hewanDTO, @PathVariable Long id,
            @PathVariable Long idHewan) {
        return hewanService.editHewan(hewanDTO, id, idHewan);
    }
}
