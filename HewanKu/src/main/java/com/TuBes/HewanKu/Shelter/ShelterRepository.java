package com.TuBes.HewanKu.Shelter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
        Optional<Shelter> findByEmail(String email);
        Shelter getByShelterAcc_Id(Long shelterAccId);
}
