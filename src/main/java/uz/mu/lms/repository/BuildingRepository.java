package uz.mu.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mu.lms.model.Building;

import java.util.List;

// todo change to proper jpa repository
public class BuildingRepository {

    public static List<Building> findAllBuildings() {
        Building buildingA = Building.builder()
                .name("A")
                .latitude(41.295318)
                .longitude(69.225499)
                .radiusInMeters(50)
                .build();

        Building buildingB = Building.builder()
                .name("B")
                .latitude(41.296364)
                .longitude(69.229462)
                .radiusInMeters(50)
                .build();

        Building buildingC = Building.builder()
                .name("B")
                .latitude(41.296206)
                .longitude(69.229291)
                .radiusInMeters(50)
                .build();

        Building home = Building.builder()
                .name("Avazxon")
                .latitude(40.581772)
                .longitude(70.923249)
                .radiusInMeters(50)
                .build();

        Building home2 = Building.builder()
                .name("Parizoda")
                .latitude(41.268521)
                .longitude(69.251866)
                .radiusInMeters(50)
                .build();

        return List.of(buildingA, buildingB, buildingC, home);
    }

}
