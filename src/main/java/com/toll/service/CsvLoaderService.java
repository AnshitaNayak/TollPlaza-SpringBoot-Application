package com.toll.service;

import com.toll.entity.TollPlaza;
import com.toll.repository.TollPlazaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class CsvLoaderService {

    private final TollPlazaRepository repository;

    @PostConstruct
    public void loadCsv() throws Exception {

        if(repository.count() > 0) {
            return; // prevent duplicate loading
        }

        ClassPathResource resource =
                new ClassPathResource("toll_plaza_india.csv");

        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(resource.getInputStream()))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                TollPlaza toll = TollPlaza.builder()
                        .longitude(Double.parseDouble(data[0].trim()))
                        .latitude(Double.parseDouble(data[1].trim()))
                        .name(data[2].trim())
                        .geoState(data[3].trim())
                        .build();

                repository.save(toll);
            }
        }
    }
}