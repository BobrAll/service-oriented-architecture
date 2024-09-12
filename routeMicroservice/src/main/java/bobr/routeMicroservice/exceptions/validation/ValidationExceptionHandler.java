package bobr.routeMicroservice.exceptions.validation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<Violation>> onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(v -> new Violation(
                        v.getRootBeanClass().getSimpleName(),
                        v.getPropertyPath().toString(),
                        v.getMessage()
                ))
                .toList();

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body(violations);
    }

}
