package uz.mu.lms.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mu.lms.model.Building;
import uz.mu.lms.repository.BuildingRepository;
import uz.mu.lms.service.BuildingService;
import uz.mu.lms.utils.LocationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    @Override
    public boolean isUserWithinUniversityZone(double userLatitude, double userLongitude) {
        List<Building> buildings = BuildingRepository.findAllBuildings();

        for (Building building : buildings) {
            LocationUtils.Rectangle boundingBox = LocationUtils.getBoundingBox(
                    building.latitude(), building.longitude(), building.radiusInMeters());

            if (isWithinBounds(userLatitude, userLongitude, boundingBox)) {
                return true;
            }
        }

        return false;
    }

    private boolean isWithinBounds(double userLatitude, double userLongitude, LocationUtils.Rectangle boundingBox) {
        return userLatitude >= boundingBox.southLat &&
                userLatitude <= boundingBox.northLat &&
                userLongitude >= boundingBox.westLon &&
                userLongitude <= boundingBox.eastLon;
    }
}

