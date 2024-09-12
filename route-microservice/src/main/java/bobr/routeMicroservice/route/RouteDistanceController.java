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
@RequestMapping(value = "/api/v1/routes/distance", produces = MediaType.APPLICATION_XML_VALUE)
public class RouteDistanceController {

    private final RouteService routeService;

    @GetMapping("/less-than/{value}")
    public List<Route> lessThan(@PathVariable double value) {
        return routeService.findAllByDistanceLessThan(value);
    }

    @GetMapping("/greater-than/{value}")
    public List<Route> greaterThan(@PathVariable double value) {
        return routeService.findAllByDistanceGreaterThan(value);
    }

    @GetMapping("/unique")
    public List<Double> unique() {
        return routeService.findDistinctDistances();
    }

}
