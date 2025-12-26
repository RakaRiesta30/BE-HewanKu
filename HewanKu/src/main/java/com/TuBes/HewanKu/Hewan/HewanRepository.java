package com.TuBes.HewanKu.Hewan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TuBes.HewanKu.Shelter.Shelter;

@Repository
public interface HewanRepository extends JpaRepository<Hewan, Long> {
    List<Hewan> getReferenceByJenisAndHargaBetween(String jenis, double hargaMin, double hargaMax);
    Shelter findShelterById(Long id);
}
