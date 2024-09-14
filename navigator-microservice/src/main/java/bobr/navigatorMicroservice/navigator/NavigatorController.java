package bobr.navigatorMicroservice.navigator;

import bobr.navigatorMicroservice.models.NavigatorRouteRequest;
import bobr.navigatorMicroservice.models.RouteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/navigator/route", produces = MediaType.APPLICATION_XML_VALUE)
public class NavigatorController {

    @Value("${route-microservice.url}")
    private String ROUTE_MICROSERVICE_URL;

    @Value("${route-microservice.port}")
    private Integer ROUTE_MICROSERVICE_PORT;

    private final RestTemplate restTemplate;

    @GetMapping("/{id-from}/{id-to}/shortest")
    public ResponseEntity<String> findRoutes(
            @PathVariable("id-from") String fromId,
            @PathVariable("id-to") String toId
    ) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(ROUTE_MICROSERVICE_URL)
                .port(ROUTE_MICROSERVICE_PORT)
                .path("/api/v1/routes")
                .queryParam("fromId", fromId)
                .queryParam("toId", toId)
                .queryParam("sortField", "distance")
                .queryParam("sortDirection", "asc")
                .queryParam("size", 1)
                .build()
                .toUri();

        return restTemplate.getForEntity(uri, String.class);
    }

    @PostMapping("/add/{id-from}/{id-to}/{distance}")
    public ResponseEntity<String> addRoute(
            @PathVariable("id-from") Integer fromId,
            @PathVariable("id-to") Integer toId,
            @PathVariable Double distance,
            @Valid @RequestBody NavigatorRouteRequest navigatorRouteRequest
    ) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(ROUTE_MICROSERVICE_URL)
                .port(ROUTE_MICROSERVICE_PORT)
                .path("/api/v1/routes")
                .build()
                .toUri();

        RouteRequest routeRequest = RouteRequest.builder()
                .name(navigatorRouteRequest.getRouteName())
                .coordinates(navigatorRouteRequest.getCoordinates())
                .fromId(fromId)
                .toId(toId)
                .distance(distance)
                .build();

        return restTemplate.postForEntity(uri, routeRequest, String.class);
    }

}
