package org.sitoolkit.ad.archetype.tips.infrastructure.code;

/**
 * このインターフェースは、コードを表す列挙型が実装すべき振る舞いを定義します。
 * 
 * @author SIToolkit
 */
public interface Code {

    /**
     * コードの名称を取得します。
     * 
     * @return コードの名称
     */
    String getLabel();

    /**
     * コードを取得します。
     * 
     * @return コード
     */
    String getValue();

}
