package bobr.navigatorMicroserviceEJB.business;

import bobr.navigatorMicroserviceEJB.models.NavigatorRouteRequest;
import bobr.navigatorMicroserviceEJB.models.RouteRequest;
import jakarta.ejb.Stateless;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;

@Stateless
@Service
public class NavigatorServiceEJB implements NavigatorService {

    @Value("${route-microservice.url}")
    private String ROUTE_MICROSERVICE_URL;

    @Value("${route-microservice.port}")
    private Integer ROUTE_MICROSERVICE_PORT;

    @Inject
    private RestTemplate restTemplate;

    @Async
    @Override
    public String findShortestRoute(String fromId, String toId) {
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

        return restTemplate.getForObject(uri, String.class);
    }

    @Async
    @Override
    public String addRoute(Integer fromId, Integer toId, Double distance, @Valid NavigatorRouteRequest navigatorRouteRequest) {
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

        return restTemplate.postForObject(uri, routeRequest, String.class);
    }
}
