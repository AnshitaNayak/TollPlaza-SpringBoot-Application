package com.toll.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "toll_plaza",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "latitude", "longitude"}))

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TollPlaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "geo_state")
    private String geoState;
}
