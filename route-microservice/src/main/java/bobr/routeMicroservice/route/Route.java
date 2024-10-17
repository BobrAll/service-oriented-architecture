package bobr.routeMicroservice.route;

import bobr.routeMicroservice.coordinates.Coordinates;
import bobr.routeMicroservice.location.Location;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Route {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "from_id")
    private Location from;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "to_id")
    private Location to;

    @Min(1)
    private Double distance;

}
