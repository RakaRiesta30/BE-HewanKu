package com.TuBes.HewanKu.Pesanan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Long> {
    long countByStatusIsNull();

    long countByStatus(String status);

    List<Pesanan> findByStatusPembayaran(String statusPemabayaran);
}
