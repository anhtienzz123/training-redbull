package trainingredbull.exception;

import org.springframework.http.HttpStatus;
import lombok.Data;
import trainingredbull.util.ErrorUtils;

@Data
public class RedbullException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorMessage errorMessage;

    public RedbullException() {
        super();
        this.errorMessage = new ErrorMessage();
    }

    public RedbullException(String errorCode) {
        super();
        this.errorMessage = ErrorUtils.getErrorResponse(errorCode);
    }

    public RedbullException(HttpStatus httpStatus) {
        super();
        this.errorMessage = ErrorUtils.getErrorResponse(String.valueOf(httpStatus.value()));
    }
}
