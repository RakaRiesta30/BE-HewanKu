package com.TuBes.HewanKu;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.TuBes.HewanKu.Pesanan.Pesanan;
import com.TuBes.HewanKu.Pesanan.PesananRepository;
import com.TuBes.HewanKu.Pesanan.PesananService;

@Service
public class FiturOtomatisService {
    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PesananService pesananService;

    @Scheduled(fixedRate = 300000, initialDelay = 60000)
    public void jalankanPengecekanOtomatis() {
        System.out.println("Sistem sedang mengecek data otomatis pada: " + LocalDateTime.now());
        List<Pesanan> pesananBelumDibayar = pesananRepository.findByStatusPembayaran("pending");
        System.out.println(pesananBelumDibayar);
        try {
            pesananService.updatePesanan(pesananBelumDibayar);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}