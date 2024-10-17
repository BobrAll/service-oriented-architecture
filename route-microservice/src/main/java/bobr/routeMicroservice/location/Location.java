package bobr.routeMicroservice.location;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ToString
public class Location {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;

    @NotNull
    private Double x;

    private Integer y;

    @NotNull
    private Float z;

    @JsonGetter(value = "id")
    public Integer getId() {
        return id;
    }

}
