package com.toll.repository;

import com.toll.entity.TollPlaza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TollPlazaRepository extends JpaRepository<TollPlaza, Long> {

    boolean existsByNameAndLatitudeAndLongitude(
            String name, Double latitude, Double longitude);
}