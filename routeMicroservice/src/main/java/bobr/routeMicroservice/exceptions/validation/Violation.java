package bobr.routeMicroservice.exceptions.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {

    private final String objectName;
    private final String fieldName;
    private final String message;

}
