package bobr.routeMicroservice.exceptions;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Violation> handleExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body(generateViolationFromException(e));
    }

    private Violation generateViolationFromException(MethodArgumentNotValidException e) {
        return new Violation(e.getObjectName(), e.getMessage());
    }

}
