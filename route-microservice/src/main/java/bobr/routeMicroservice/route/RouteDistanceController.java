package bobr.routeMicroservice.route;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/route/distance", produces = MediaType.APPLICATION_XML_VALUE)
public class RouteDistanceController {

    private final RouteService routeService;

    @GetMapping("/less-than/{value}")
    public List<Route> lessThan(@PathVariable double value) {
        return routeService.findAll().stream()
                .filter(r -> r.getDistance() < value)
                .toList();
    }

    @GetMapping("/greater-than/{value}")
    public List<Route> greaterThan(@PathVariable double value) {
        return routeService.findAll().stream()
                .filter(r -> r.getDistance() > value)
                .toList();
    }

    @GetMapping("/unique")
    public List<Route> unique() {
        return routeService.findAll().stream()
                .distinct()
                .toList();
    }

}
