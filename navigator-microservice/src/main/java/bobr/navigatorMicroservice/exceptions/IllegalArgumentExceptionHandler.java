package bobr.navigatorMicroservice.exceptions;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Violation> handleExceptions(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body(new Violation("", e.getMessage()));
    }

}
