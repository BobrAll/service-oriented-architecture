package bobr.navigatorMicroservice.navigator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
