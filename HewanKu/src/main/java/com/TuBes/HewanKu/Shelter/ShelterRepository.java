package com.TuBes.HewanKu.Shelter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.TuBes.HewanKu.Hewan.Hewan;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
        Optional<Shelter> findByEmail(String email);

        Shelter getByShelterAcc_Id(Long shelterAccId);

        ShelterAcc getShelterAccById(Long Id);

        @Query("SELECT h FROM Shelter s JOIN s.daftarHewan h ORDER BY s.hewanDibeli DESC")
        List<Hewan> findHewanOrderByShelter();
}
