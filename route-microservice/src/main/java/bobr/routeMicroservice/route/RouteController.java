package bobr.routeMicroservice.route;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/routes", produces = MediaType.APPLICATION_XML_VALUE)
public class RouteController {

    public final RouteService routeService;

    @GetMapping
    public List<Route> getAllRoutes(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long coordX,
            @RequestParam(required = false) Long coordY,
            @RequestParam(required = false) Double fromX,
            @RequestParam(required = false) Integer fromY,
            @RequestParam(required = false) Float fromZ,
            @RequestParam(required = false) Double toX,
            @RequestParam(required = false) Integer toY,
            @RequestParam(required = false) Float toZ,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Route> spec = Specification
                .where(RouteSpecification.hasId(id))
                .and(RouteSpecification.hasName(name))
                .and(RouteSpecification.hasCoordinates(coordX, coordY))
                .and(RouteSpecification.hasLocationFrom(fromX, fromY, fromZ))
                .and(RouteSpecification.hasLocationTo(toX, toY, toZ));

        return routeService.findAll(spec, pageable).toList();
    }

    @GetMapping("/{id}")
    public Route getRoute(@PathVariable Integer id) {
        return routeService.findById(id);
    }

    @PostMapping
    public void createRoute(@Valid @RequestBody RouteRequest routeRequest) {
        routeService.add(routeRequest);
    }

    @PutMapping("/{id}")
    public void updateRoute(
            @PathVariable Integer id,
            @Valid @RequestBody RouteRequest routeRequest
    ) {
        Route updatedRoute = new Route(routeRequest);
        routeService.update(id, updatedRoute);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Integer id) {
        routeService.delete(id);
    }

}
