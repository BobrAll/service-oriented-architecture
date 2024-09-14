package bobr.routeMicroservice.location;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Location {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private Double x;

    private Integer y;

    @NotNull
    private Float z;

}
