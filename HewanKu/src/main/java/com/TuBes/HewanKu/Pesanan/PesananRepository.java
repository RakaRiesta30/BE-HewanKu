package com.TuBes.HewanKu.Pesanan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TuBes.HewanKu.Shelter.Shelter;

@Repository
public interface PesananRepository extends JpaRepository<Pesanan, Long> {
    long countByStatusIsNull();

    long countByStatus(String status);

    List<Pesanan> findByStatusPembayaran(String statusPembayaran);

    List<Pesanan> findByStatusPembayaranAndShelter(String statusPembayaran, Shelter shelter);
}
