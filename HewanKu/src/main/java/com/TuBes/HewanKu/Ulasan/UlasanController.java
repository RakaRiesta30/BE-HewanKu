// package com.TuBes.HewanKu.Ulasan;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/ulasan/{id}")
// public class UlasanController {
//     @Autowired
//     private UlasanService ulasanService;

//     @PostMapping("/{penggunaId}")
//     public Map<String, Object> createUlasan(@RequestBody UlasanDTO ulasanDTO, @PathVariable Long id, @PathVariable Long penggunaId){
//         return ulasanService.createUlasan(ulasanDTO, id, penggunaId);
//     }
// }
