package com.TuBes.HewanKu.Ulasan;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.BaseResponse;
import com.TuBes.HewanKu.Hewan.HewanRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UlasanService {
    private final UlasanRepository ulasanRepository;
    private final BaseResponse res;
    private final HewanRepository hewanRepository;

    @Autowired
    public UlasanService(HewanRepository hewanRepository, UlasanRepository ulasanRepository, BaseResponse res) {
        this.hewanRepository = hewanRepository;
        this.ulasanRepository = ulasanRepository;
        this.res = res;
    }

    public Map<String, Object> createUlasan(UlasanDTO ulasanDTO, Long id, Long idPengguna){
        Map<String, Object> response = new LinkedHashMap<>();
        hewanRepository.findById(id)
            .ifPresentOrElse(hewan -> {
                Ulasan ulasan = new Ulasan(
                    LocalDate.now(), 
                    hewan, 
                    ulasanDTO.getKomen(),
                    ulasanDTO.getRating());
                ulasanRepository.save(ulasan);
                List<Double> rating = ulasanRepository.findById(id)
                    .stream()
                    .map(Ulasan::getRating)
                    .toList();
                Double sum = 0.0;
                for (int i = 0; i < rating.size(); i++){
                    sum += rating.get(i);
                }
                sum = sum/rating.size();
                hewan.setRating(sum);
                hewanRepository.save(hewan);
                response.putAll(res.CREATED("Komen telah terbuat", null, null));
            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null, "UNAUTHORIZED, Hewan tidak ditemukan")));
        return response;
    }

    public Map<String, Object> viewUlasan(Long id){
        Map<String, Object> response = new LinkedHashMap<>();
        hewanRepository.findById(id)
            .ifPresentOrElse(hewan -> {
                response.putAll(res.OK("Hewan ditemukan", hewan.getUlasan(), null));
            }, () -> response.putAll(res.UNAUTHORIZED("Hewan tidak ditemukan", null, "UNAUTHORIZED, Hewan tidak ditemukan")));
        return response;
    }
    
}
