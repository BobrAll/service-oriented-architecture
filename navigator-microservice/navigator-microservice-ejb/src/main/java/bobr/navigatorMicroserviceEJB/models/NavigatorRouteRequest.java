package bobr.navigatorMicroserviceEJB.models;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class NavigatorRouteRequest {

    private String routeName;

    @Valid
    private bobr.navigatorMicroserviceEJB.models.Coordinates coordinates;

}
