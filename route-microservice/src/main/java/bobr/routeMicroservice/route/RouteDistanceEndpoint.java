package bobr.routeMicroservice.route;

import bobr.routeMicroservice.mappers.RouteMapper;
import https.help_me.please.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.stream.Collectors;

import static bobr.routeMicroservice.config.WebServiceConfig.SOAP_NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class RouteDistanceEndpoint {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getDistancesLessThanValueRequest")
    @ResponsePayload
    public GetDistancesLessThanValueResponse getDistances(@RequestPayload GetDistancesLessThanValueRequest request) {
        var response = new GetDistancesLessThanValueResponse();
        response.getRoutes().addAll(
                routeService.findAllByDistanceLessThan(request.getValue()).stream()
                        .map(routeMapper::map)
                        .collect(Collectors.toList())
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getDistancesGreaterThanValueRequest")
    @ResponsePayload
    public GetDistancesGreaterThanValueResponse getDistances(@RequestPayload GetDistancesGreaterThanValueRequest request) {
        var response = new GetDistancesGreaterThanValueResponse();
        response.getRoutes().addAll(
                routeService.findAllByDistanceGreaterThan(request.getValue()).stream()
                        .map(routeMapper::map)
                        .collect(Collectors.toList())
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getUniqueDistancesRequest")
    @ResponsePayload
    public GetUniqueDistancesResponse getUniqueDistances(@RequestPayload GetUniqueDistancesRequest request) {
        var response = new GetUniqueDistancesResponse();
        response.getDistances().addAll(routeService.findDistinctDistances());
        return response;
    }

}
