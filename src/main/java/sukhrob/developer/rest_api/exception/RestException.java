package sukhrob.developer.rest_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException{

    @Getter
    private HttpStatus status;
    private String message;
    private Object object;

    public RestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public RestException(HttpStatus status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    private HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }

}
