package bobr.navigatorMicroservice.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteRequest {

    private String name;

    private Coordinates coordinates;

    private Integer fromId;

    private Integer toId;

    private Double distance;

}
