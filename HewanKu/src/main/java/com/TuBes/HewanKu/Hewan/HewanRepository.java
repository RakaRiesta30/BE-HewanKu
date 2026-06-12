package com.TuBes.HewanKu.Hewan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HewanRepository extends JpaRepository<Hewan, Long> {
    List<Hewan> findByJenisAndHargaBetween(String jenis, Double hargaMin, Double hargaMax);

    List<Hewan> findByJenis(String jenis);

    List<Hewan> findByHargaBetween(Double hargaMin, Double hargaMax);

    Long findShelter_IdById(Long id);

    long countByShelter_Id(Long shelterId);

    List<Hewan> findAllByOrderByRatingDesc();

    List<Hewan> findAllByOrderByJumlahFavoritDesc();
}
