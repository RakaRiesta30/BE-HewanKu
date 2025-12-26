package com.TuBes.HewanKu.Ulasan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UlasanRepository extends JpaRepository<Ulasan, Long> {
    List<Ulasan> findByHewan_Id(Long hewanId);
}
