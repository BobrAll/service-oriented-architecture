package bobr.navigatorMicroserviceWeb.navigator;

import bobr.navigatorMicroserviceEJB.business.NavigatorService;
import bobr.navigatorMicroserviceEJB.models.NavigatorRouteRequest;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/navigator/route", produces = MediaType.APPLICATION_XML_VALUE)
public class NavigatorController {

    @EJB
    private NavigatorService navigatorService;

    @GetMapping("/{id-from}/{id-to}/shortest")
    public ResponseEntity<String> findRoutes(
            @PathVariable("id-from") String fromId,
            @PathVariable("id-to") String toId
    ) {
        String result = navigatorService.findShortestRoute(fromId, toId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add/{id-from}/{id-to}/{distance}")
    public ResponseEntity<String> addRoute(
            @PathVariable("id-from") Integer fromId,
            @PathVariable("id-to") Integer toId,
            @PathVariable Double distance,
            @Valid @RequestBody NavigatorRouteRequest navigatorRouteRequest
    ) {
        String result = navigatorService.addRoute(fromId, toId, distance, navigatorRouteRequest);
        return ResponseEntity.ok(result);
    }
}