package bobr.routeMicroservice.location;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public void save(Location location) {
        locationRepository.save(location);
    }

    public Location findById(Integer id) {
        return locationRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        locationRepository.deleteById(id);
    }
    
}
