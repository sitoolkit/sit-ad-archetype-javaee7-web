package org.sitoolkit.ad.archetype.tips.infrastructure.search;

/**
 * このクラスは、検索条件の比較演算子を表すCDです。
 * 
 * @author SIToolkit
 */
public enum OperatorCd {
    /**
     * 等価(equal)を表す演算子です。
     */
    EQ("="),
    /**
     * 不等価(not equal)を表す演算子です。
     */
    NE("<>"),
    /**
     * 大なり(greater than)を表す演算子です。
     */
    GT(">"),
    /**
     * 以上(greater than equal)を表す演算子です。
     */
    GE(">="),
    /**
     * 小なり(less than)を表す演算子です。
     */
    LT("<"),
    /**
     * 以下(less than equal)を表す演算子です。
     */
    LE("<="),
    /**
     * 部分一致(like)を表す演算子です。
     */
    LK("LIKE"),
    /**
     * 複数一致(in)を表す演算子です。
     */
    IN("IN");

    private String str;

    private OperatorCd(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
