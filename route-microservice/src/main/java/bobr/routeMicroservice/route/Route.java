package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_id")
    private Location from;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id")
    private Location to;

    @Min(1)
    private Double distance;

    public Route(RouteRequest routeRequest) {
        this.name = routeRequest.getName();
        this.coordinates = routeRequest.getCoordinates();
        this.from = routeRequest.getFrom();
        this.to = routeRequest.getTo();
        this.distance = routeRequest.getDistance();

        this.creationDate = LocalDateTime.now();
    }

}
