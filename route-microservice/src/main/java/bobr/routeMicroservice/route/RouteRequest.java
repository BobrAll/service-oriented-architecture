package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class RouteRequest {

    private String name;

    @Valid
    private Coordinates coordinates;

    @Valid
    private Location from;

    @Valid
    private Location to;

    private Double distance;

}
