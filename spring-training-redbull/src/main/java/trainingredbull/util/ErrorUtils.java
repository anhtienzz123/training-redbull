package trainingredbull.util;

import java.util.Properties;
import trainingredbull.exception.ErrorMessage;

public class ErrorUtils {

    private static final String ERROR_MESSAGE_FILE_NAME = "errorMessage.properties";

    private static Properties properties;

    static {
        properties = CommonUtils.loadResourceProperties(ERROR_MESSAGE_FILE_NAME);
    }

    private ErrorUtils() {}

    public static ErrorMessage getErrorResponse(String errorCode) {
        String errorMessage = properties.getProperty(String.valueOf(errorCode));
        return new ErrorMessage(errorCode, errorMessage);
    }
}
