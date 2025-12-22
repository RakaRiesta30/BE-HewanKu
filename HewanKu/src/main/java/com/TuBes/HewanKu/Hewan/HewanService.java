package com.TuBes.HewanKu.Hewan;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
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
    private final BaseResponse res;

    @Autowired
    public HewanService(HewanRepository hewanRepository, PenggunaRepository penggunaRepository, BaseResponse res, ShelterRepository shelterRepository) {
        this.hewanRepository = hewanRepository;
        this.penggunaRepository = penggunaRepository;
        this.res = res;
        this.shelterRepository = shelterRepository;
    }

    public Map<String, Object> createHewan(HewanDTO hewanDTO, Long id){
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                Hewan hewan = new Hewan(
                    hewanDTO.getHarga(),
                    hewanDTO.getJenis(),
                    hewanDTO.getKesehatan(),
                    hewanDTO.getNama(),
                    0.0,
                    shelter,
                    hewanDTO.getStatus(),
                    LocalDate.now()
                );
                shelter.getDaftarHewan().add(hewan);
                hewanRepository.save(hewan);
                shelterRepository.save(shelter);
                response.putAll(res.CREATED("Hewan telah terdaftar", null, null));
            }, () -> response.putAll(res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> viewHewan(String role, Long id){
        Map<String, Object> response = new LinkedHashMap<>();
        if (role.equals("shelter")){
            shelterRepository.findById(id)
                .ifPresentOrElse(shelter -> {
                    List<Hewan> daftarHewan = shelter.getDaftarHewan();
                    response.putAll(res.OK("Hewan ditemukan", daftarHewan, null));
                }, () -> response.putAll(res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        } else {
            penggunaRepository.findById(id)
                .ifPresentOrElse(pengguna -> {
                    List<Shelter> daftarShelter = shelterRepository.findAll();
                    response.putAll(res.OK("Shelter ditemukan", daftarShelter, null));
                }, () -> response.putAll(res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        }
        return response;
    }

    public Map<String, Object> editHewan(HewanDTO hewanDTO, Long id, Long idHewan){
        Map<String, Object> response = new LinkedHashMap<>();
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
                        response.putAll(res.OK("Hewan telah diubah", null, null));
                    }, () -> {
                        response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null, "Unauthorized, Hewan tidak ditemukan "));
                    });
            }, () -> response.putAll(res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> deleteHewan(Long id, Long idHewan){
        Map<String, Object> response = new LinkedHashMap<>();
        shelterRepository.findById(id)
            .ifPresentOrElse(shelter -> {
                hewanRepository.findById(idHewan)
                    .ifPresentOrElse(hewan -> {
                        hewanRepository.delete(hewan);
                        response.putAll(res.OK("Hewan telah dihapus", null, null));
                    }, () -> {
                        response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null, "Unauthorized, Hewan tidak ditemukan "));
                    });
            }, () -> response.putAll(res.UNAUTHORIZED("Shelter tidak ditemukan", null, "Unauthorized, Shelter tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> filterHewan(Long id, FilterDTO filterDTO){
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
            .ifPresentOrElse(pengguna -> {
                List<Hewan> hewanList = hewanRepository.getReferenceByJenisAndHargaBetween(filterDTO.getJenis(), filterDTO.getHargaMin(), filterDTO.getHargaMax());
                response.putAll(res.OK("Hewan telah difilter", hewanList, null));
            }, () -> response.putAll(res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }

    public Map<String, Object> detailHewan(Long id, Long idHewan){
        Map<String, Object> response = new LinkedHashMap<>();
        penggunaRepository.findById(id)
            .ifPresentOrElse(pengguna -> {
                hewanRepository.findById(idHewan)
                    .ifPresentOrElse(hewan -> {
                        response.putAll(res.OK("Hewan ditemukan", hewan, null));
                    }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null, "Unauthorized, Hewan tidak ditemukan")));
            }, () -> response.putAll(res.UNAUTHORIZED("Pengguna tidak ditemukan", null, "Unauthorized, Pengguna tidak ditemukan ")));
        return response;
    }
}
