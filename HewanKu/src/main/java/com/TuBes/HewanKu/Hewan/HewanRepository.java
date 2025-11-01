package com.TuBes.HewanKu.Hewan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HewanRepository extends JpaRepository<Hewan, Long> {

}
