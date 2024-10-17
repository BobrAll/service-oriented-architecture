package bobr.routeMicroservice.route;

import bobr.routeMicroservice.mappers.RouteMapper;
import https.help_me.please.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import static bobr.routeMicroservice.config.WebServiceConfig.SOAP_NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class RouteEndpoint {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "createRouteRequest")
    @ResponsePayload
    public void createRoute(@RequestPayload CreateRouteRequest request) {
        routeService.add(request);
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getRouteRequest")
    @ResponsePayload
    public GetRouteResponse getRoute(@RequestPayload GetRouteRequest request) {
        GetRouteResponse response = new GetRouteResponse();
        response.setRoute(routeMapper.map(routeService.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getAllRoutesRequest")
    @ResponsePayload
    public GetAllRoutesResponse getAllRoutes(@RequestPayload GetAllRoutesRequest request) {
        Sort sort = request.getSortDirection().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(request.getSortField()).ascending() : Sort.by(request.getSortField()).descending();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Specification<Route> spec = Specification
                .where(RouteSpecification.hasId(request.getId()))
                .and(RouteSpecification.hasFromId(request.getFromId()))
                .and(RouteSpecification.hasToId(request.getToId()))
                .and(RouteSpecification.hasName(request.getName()))
                .and(RouteSpecification.hasCoordinates(request.getCoordX(), request.getCoordY()))
                .and(RouteSpecification.hasLocationFrom(request.getFromX(), request.getFromY(), request.getFromZ()))
                .and(RouteSpecification.hasLocationTo(request.getToX(), request.getToY(), request.getToZ()));

        GetAllRoutesResponse response = new GetAllRoutesResponse();
        response.getRoutes().addAll(
                routeService.findAll(spec, pageable)
                        .map(routeMapper::map)
                        .toList()
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "updateRouteRequest")
    @ResponsePayload
    public void updateRoute(@RequestPayload UpdateRouteRequest request) {
        routeService.update(request.getId(), routeMapper.map(request));
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "deleteRouteRequest")
    @ResponsePayload
    public void deleteRoute(@RequestPayload DeleteRouteRequest request) {
        routeService.delete(request.getId());
    }

}
