package bobr.routeMicroservice.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Integer id;

    @NotNull
    private Double x;

    private Integer y;

    @NotNull
    private Float z;

}
