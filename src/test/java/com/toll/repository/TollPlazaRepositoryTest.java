package com.toll.repository;

import com.toll.entity.TollPlaza;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TollPlazaRepositoryTest {

    @Autowired
    private TollPlazaRepository repository;

    @Test
    void shouldSaveAndFetchToll() {

        TollPlaza toll = TollPlaza.builder()
                .name("Test Toll")
                .latitude(12.0)
                .longitude(77.0)
                .build();

        repository.save(toll);

        List<TollPlaza> list = repository.findAll();

        assertEquals(1, list.size());
        assertEquals("Test Toll", list.get(0).getName());
    }
}