package com.TuBes.HewanKu.Shelter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TuBes.HewanKu.Hewan.HewanDTO;
import com.TuBes.HewanKu.Hewan.HewanService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/shelter")
public class ShelterController {

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private HewanService hewanService;

    public ShelterController(ShelterRepository shelterRepository) {
    }

    @PostMapping("/auth/register")
    public Map<String, Object> register(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.register(shelterDTO);
    }

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.login(shelterDTO.getEmail(), shelterDTO.getPassword());
    }

    @PostMapping("/auth/forgot")
    public Map<String, Object> forgotPassword(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.forgotPassword(shelterDTO.getEmail());
    }

    @PostMapping("/auth/verify")
    public Map<String, Object> verifyOtp(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.verifyOtp(shelterDTO.getOtp(), shelterDTO.getEmail());
    }

    @PostMapping("/auth/change")
    public Map<String, Object> changePassword(@RequestBody ShelterDTO shelterDTO) {
        return shelterService.changePassword(shelterDTO.getPassword(), shelterDTO.getRepassword(),
                shelterDTO.getEmail());
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createShelter(@ModelAttribute ShelterAccDTO shelterAccDTO,
            Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.createShelter(shelterAccDTO, idShelter);
    }

    @PatchMapping("/editPenjual")
    public Map<String, Object> editPenjual(@RequestBody ShelterDTO shelterDTO, Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.editPenjual(shelterDTO, idShelter);
    }

    @PatchMapping(value = "/editShelter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> editShelter(@ModelAttribute ShelterAccDTO shelterAccDTO,
            Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.editShelter(shelterAccDTO, idShelter);
    }

    @GetMapping("/view")
    public Map<String, Object> viewShelter(Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.viewShelter(idShelter);
    }

    @DeleteMapping("/delete/{idHewan}")
    public Map<String, Object> deleteHewan(Authentication authentication, @PathVariable Long idHewan) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.deleteHewan(idShelter, idHewan);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> createHewan(@ModelAttribute HewanDTO hewanDTO, Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.createHewan(hewanDTO, idShelter);
    }

    @PatchMapping(value = "/edit/{idHewan}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> editHewan(@ModelAttribute HewanDTO hewanDTO, Authentication authentication,
            @PathVariable Long idHewan) {
        Long idShelter = (Long) authentication.getPrincipal();
        return hewanService.editHewan(hewanDTO, idShelter, idHewan);
    }

    @GetMapping("/view/shelterAcc")
    public Map<String, Object> viewShelterAcc(Authentication authentication) {
        Long id = (Long) authentication.getPrincipal();
        return shelterService.viewShelterAcc(id);
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request, Authentication authentication) {
        Long idShelter = (Long) authentication.getPrincipal();
        return shelterService.logout(request, idShelter);
    }
}
