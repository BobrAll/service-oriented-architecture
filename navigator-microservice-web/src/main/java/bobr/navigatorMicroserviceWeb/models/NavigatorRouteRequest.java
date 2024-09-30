package bobr.navigatorMicroserviceWeb.models;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class NavigatorRouteRequest {

    private String routeName;

    @Valid
    private Coordinates coordinates;

}
