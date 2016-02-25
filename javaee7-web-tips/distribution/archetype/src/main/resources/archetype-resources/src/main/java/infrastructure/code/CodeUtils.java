#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.code;

import java.lang.reflect.InvocationTargetException;

import ${package}.infrastructure.IllegalCodingException;

/**
 * このクラスは、コードの操作に関するメソッドを提供するユーティリティです。
 * 
 * @author SIToolkit
 */
public class CodeUtils {

    private CodeUtils() {
    }

    /**
     * コード値をコードにデコードします。 コード値に対応するコードが無い場合はnullを返します。
     * 
     * @param <T>
     *            デコード先となるコードの型
     * @param codeValue
     *            コード値
     * @param codeType
     *            デコード先となるコードの型
     * @return コード
     */
    public static <T extends Code> T decode(String codeValue, Class<T> codeType) {
        return decode(codeValue, codeType, null);
    }

    /**
     * コード値をコードにデコードします。
     * 
     * @param <T>
     *            デコード先となるコードの型
     * @param codeValue
     *            コード値
     * @param codeType
     *            デコード先となるコードの型
     * 
     * @param defaultValue
     *            コード値に対応するコードが無い場合のコード
     * @return コード
     */
    @SuppressWarnings("unchecked")
    public static <T extends Code> T decode(String codeValue, Class<T> codeType, T defaultValue) {
        try {
            T[] values = (T[]) codeType.getMethod("values").invoke(null);
            for (T code : values) {
                if (code.getValue().equals(codeValue)) {
                    return code;
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new IllegalCodingException(codeType + "は列挙型で実装してください。");
        }

        return defaultValue;

    }

}
