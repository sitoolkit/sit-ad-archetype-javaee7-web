#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure;

public class FrameworkException extends RuntimeException {

    public FrameworkException() {
        super();
    }

    public FrameworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(Throwable cause) {
        super(cause);
    }

}
