package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import lombok.Data;

import javax.validation.Valid;

@Data
public class RouteRequest {

    private String name;

    @Valid
    private Coordinates coordinates;

    private Integer fromId;

    private Integer toId;

    private Double distance;

}
