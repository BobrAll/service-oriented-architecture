package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class RouteRequest {

    private String name;

    @Valid
    private Coordinates coordinates;

    private Integer fromId;

    private Integer toId;

    private Double distance;

}
