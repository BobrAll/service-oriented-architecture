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
        final int IDX_OF_ARG_WITH_INFORMATION = 1;

        String[] parts = e.getDetailMessageArguments()[IDX_OF_ARG_WITH_INFORMATION]
                .toString()
                .split(":", 2);

        String fieldName = parts[0].trim();
        String message = parts[1].trim();

        return new Violation(fieldName, message);
    }

}
