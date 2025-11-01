package com.TuBes.HewanKu.Hewan;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.Pengguna.PenggunaRepository;
import com.TuBes.HewanKu.Shelter.Shelter;
import com.TuBes.HewanKu.Shelter.ShelterRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HewanService {
    private final HewanRepository hewanRepository;
    private final ShelterRepository shelterRepository;
    private final PenggunaRepository penggunaRepository;

    @Autowired
    public HewanService(HewanRepository hewanRepository, PenggunaRepository penggunaRepository, ShelterRepository shelterRepository) {
        this.hewanRepository = hewanRepository;
        this.penggunaRepository = penggunaRepository;
        this.shelterRepository = shelterRepository;
    }

    public Map<String, Object> createHewan(HewanDTO hewanDTO, Long id){
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                Hewan hewan = new Hewan(
                    hewanDTO.getHarga(),
                    hewanDTO.getJenis(),
                    hewanDTO.getKesehatan(),
                    hewanDTO.getNama(),
                    hewanDTO.getStatus(),
                    LocalDate.now(),
                    shelter
                );
                shelter.getDaftarHewan().add(hewan);
                hewanRepository.save(hewan);
                shelterRepository.save(shelter);
                response.put("Pesan", "Hewan telah ditambahkan");
            }, () -> response.put("Pesan", "Shelter tidak ditemukan"));
        return response;
    }

    public Map<String, Object> viewHewan(String role, Long id){
        Map<String, Object> response = new HashMap<>();
        if (role.equals("shelter")){
            shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    List<Hewan> daftarHewan = shelter.getDaftarHewan();
                    response.put("Data hewan", daftarHewan);
                }, () -> response.put("Pesan", "Shelter tidak ditemukan"));
        } else {
            penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    List<Shelter> daftarShelter = shelterRepository.findAll();
                    response.put("Data hewan", daftarShelter);
                }, () -> response.put("Pesan", "Pengguna tidak ditemukan"));
        }
        return response;
    }

    public Map<String, Object> editHewan(HewanDTO hewanDTO, Long id, Long idHewan){
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                hewanRepository.findById(idHewan)
                    .ifPresentOrElse(hewan -> {
                        hewan.setHarga(hewanDTO.getHarga());
                        hewan.setJenis(hewanDTO.getJenis());
                        hewan.setKesehatan(hewanDTO.getKesehatan());
                        hewan.setNama(hewanDTO.getNama());
                        hewan.setStatus(hewanDTO.getStatus());
                        hewan.setUpdatedDate(LocalDate.now());
                        hewan.setShelter(shelter);
                        hewanRepository.save(hewan);
                        response.put("Pesan", "Hewan telah diubah");
                    }, () -> {
                        response.put("Pesan", "Hewan tidak ditemukan");
                    });
            }, () -> response.put("Pesan", "Shelter tidak ditemukan"));
        return response;
    }

    public Map<String, Object> deleteHewan(Long id, Long idHewan){
        Map<String, Object> response = new HashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                hewanRepository.findById(idHewan)
                    .ifPresentOrElse(hewan -> {
                        hewanRepository.delete(hewan);
                        response.put("Pesan", "Hewan telah dihapus");
                    }, () -> {
                        response.put("Pesan", "Hewan tidak ditemukan");
                    });
            }, () -> response.put("Pesan", "Shelter tidak ditemukan"));
        return response;
    }
}
