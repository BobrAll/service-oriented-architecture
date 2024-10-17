package bobr.routeMicroservice.location;

import bobr.routeMicroservice.mappers.LocationMapper;
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
public class LocationEndpoint {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "createLocationRequest")
    @ResponsePayload
    public void createLocation(@RequestPayload CreateLocationRequest request) {
        locationService.save(locationMapper.map(request));
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getLocationRequest")
    @ResponsePayload
    public GetLocationResponse getLocationById(@RequestPayload GetLocationRequest request) {
        GetLocationResponse response = new GetLocationResponse();
        response.setLocation(
                locationMapper.map(locationService.findById(request.getId()))
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "getAllLocationsRequest")
    @ResponsePayload
    public GetAllLocationsResponse getAllLocations(@RequestPayload GetAllLocationsRequest request) {
        GetAllLocationsResponse response = new GetAllLocationsResponse();
        response.getLocations().addAll(
                locationService.findAll().stream()
                        .map(locationMapper::map)
                        .collect(Collectors.toList())
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "updateLocationRequest")
    @ResponsePayload
    public void updateLocation(@RequestPayload UpdateLocationRequest request) {
        locationService.save(locationMapper.map(request));
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE_URI, localPart = "deleteLocationRequest")
    @ResponsePayload
    public void deleteLocation(@RequestPayload DeleteLocationRequest request) {
        locationService.deleteById(request.getId());
    }

}
