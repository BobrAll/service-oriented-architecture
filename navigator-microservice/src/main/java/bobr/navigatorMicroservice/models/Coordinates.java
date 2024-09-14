package bobr.navigatorMicroservice.models;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinates {

    private Long x;

    @Max(471)
    private Long y;

}

