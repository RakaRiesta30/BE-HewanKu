package com.TuBes.HewanKu.Shelter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterAccRepository extends JpaRepository<ShelterAcc, Long> {
        Optional<ShelterAcc> findByShelter_Id(Long shelterId);
}
