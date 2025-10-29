package com.TuBes.HewanKu.Pengguna;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, Long> {
    Optional<Pengguna> findByEmail(String email);
}
