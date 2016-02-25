#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure;

/**
 * 実装が不正であることを表す例外です。
 * 
 * @author SIToolkit
 *
 */
public class IllegalCodingException extends RuntimeException {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    public IllegalCodingException() {
        super();
    }

    public IllegalCodingException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IllegalCodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCodingException(String message) {
        super(message);
    }

    public IllegalCodingException(Throwable cause) {
        super(cause);
    }

}
