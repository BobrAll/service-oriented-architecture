package bobr.routeMicroservice.location;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/locations", produces = MediaType.APPLICATION_XML_VALUE)
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Location getLocation(@PathVariable Integer id) {
        return locationService.findById(id);
    }

    @PostMapping
    public void createLocation(@Valid @RequestBody Location location) {
        locationService.save(location);
    }

    @PutMapping("/{id}")
    public void updateLocation(@PathVariable Integer id, @Valid @RequestBody Location location) {
        location.setId(id);
        locationService.save(location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteById(id);
    }

}
