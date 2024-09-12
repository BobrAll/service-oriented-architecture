package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import lombok.Data;

@Data
public class RouteRequest {

    private String name;
    private Coordinates coordinates;
    private Location from;
    private Location to;
    private Double distance;

}
