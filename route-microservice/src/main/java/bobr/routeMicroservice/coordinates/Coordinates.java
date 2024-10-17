package bobr.routeMicroservice.coordinates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;

@Data
@Entity
@ToString
public class Coordinates {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    private Long x;

    @Max(471)
    private Long y;

}
