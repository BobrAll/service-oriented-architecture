package bobr.routeMicroservice.coordinates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
@Entity
public class Coordinates {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    private Long x;

    @Max(471)
    private Long y;

}
