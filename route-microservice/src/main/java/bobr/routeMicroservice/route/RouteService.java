package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import bobr.routeMicroservice.location.LocationService;
import bobr.routeMicroservice.mappers.CoordinateMapper;
import https.help_me.please.CreateRouteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final LocationService locationService;
    private final CoordinateMapper coordinateMapper;

    public Route findById(Integer id) {
        return routeRepository.findById(id).get();
    }

    @Transactional
    public Route add(CreateRouteRequest routeRequest) {
        return routeRepository.save(createFromRouteRequest(routeRequest));
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Page<Route> findAll(Specification<Route> specification, Pageable pageable) {
        return routeRepository.findAll(specification, pageable);
    }

    public void update(Integer id, Route route) {
        Route routeToUpdate = routeRepository.findById(id).get();

        routeToUpdate.setName(route.getName());

        routeToUpdate.setDistance(route.getDistance());

        Coordinates coordinates = routeToUpdate.getCoordinates();

        coordinates.setX(route.getCoordinates().getX());
        coordinates.setY(route.getCoordinates().getY());

        routeRepository.save(routeToUpdate);
    }

    public void delete(Integer id) {
        routeRepository.deleteById(id);
    }

    public List<Route> findAllByDistanceGreaterThan(Double distance) {
        return routeRepository.findAllByDistanceGreaterThan(distance);
    }

    public List<Route> findAllByDistanceLessThan(Double distance) {
        return routeRepository.findAllByDistanceLessThan(distance);
    }

    public List<Double> findDistinctDistances() {
        return routeRepository.findDistinctDistances();
    }

    @Transactional
    public Route createFromRouteRequest(CreateRouteRequest routeRequest) {
        Location from = locationService.findById(routeRequest.getFromId());
        Location to = locationService.findById(routeRequest.getToId());

        return Route.builder()
                .name(routeRequest.getName())
                .coordinates(coordinateMapper.map(routeRequest.getCoordinates()))
                .from(from)
                .to(to)
                .distance(routeRequest.getDistance())
                .creationDate(LocalDateTime.now())
                .build();
    }

}
