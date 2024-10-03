package bobr.navigatorMicroserviceEJB.business;

import bobr.navigatorMicroserviceEJB.models.NavigatorRouteRequest;

import jakarta.ejb.Remote;

@Remote
public interface NavigatorService {
    String findShortestRoute(String fromId, String toId);
    String addRoute(Integer fromId, Integer toId, Double distance, NavigatorRouteRequest request);
}

