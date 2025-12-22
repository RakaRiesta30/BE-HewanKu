// package com.TuBes.HewanKu.Ulasan;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.LinkedHashMap;
// import java.util.Map;

// import javax.transaction.Transactional;

// import com.TuBes.HewanKu.Hewan.HewanRepository;

// @Service
// @Transactional
// public class UlasanService {
//     private final UlasanRepository ulasanRepository;
//     private final BaseResponse res;
//     private final HewanRepository hewanRepository;

//     @Autowired
//     public UlasanService(HewanRepository hewanRepository, UlasanRepository ulasanRepository) {
//         this.hewanRepository = hewanRepository;
//         this.ulasanRepository = ulasanRepository;
//     }

//     public Map<String, Object> createUlasan(UlasanDTO ulasanDTO, Long id, Long idPengguna){
//         Map<String, Object> response = new LinkedHashMap<>();
//         hewanRepository.findById()
//     }
    
// }
