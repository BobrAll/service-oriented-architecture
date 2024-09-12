package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public Route findById(Integer id) {
        return routeRepository.findById(id).get();
    }

    public Route add(RouteRequest routeRequest) {
        return routeRepository.save(new Route(routeRequest));
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Page<Route> findAll(Specification<Route> specification, Pageable pageable) {
        return routeRepository.findAll(specification, pageable);
    }

    public void update(Integer id, Route route) {
        Route routeToUpdate = routeRepository.findById(id).get();

        if (route.getName() != null)
            routeToUpdate.setName(route.getName());

        if (route.getDistance() != null)
            routeToUpdate.setDistance(route.getDistance());

        if (route.getCoordinates() != null) {
            Coordinates coordinates = routeToUpdate.getCoordinates();

            coordinates.setX(route.getCoordinates().getX());
            coordinates.setY(route.getCoordinates().getY());
        }

        if (route.getFrom() != null) {
            Location from = routeToUpdate.getFrom();

            from.setX(route.getFrom().getX());
            from.setY(route.getFrom().getY());
            from.setZ(route.getFrom().getZ());
        }

        if (route.getTo() != null) {
            Location to = routeToUpdate.getTo();

            to.setX(route.getTo().getX());
            to.setY(route.getTo().getY());
            to.setZ(route.getTo().getZ());
        }

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

}
