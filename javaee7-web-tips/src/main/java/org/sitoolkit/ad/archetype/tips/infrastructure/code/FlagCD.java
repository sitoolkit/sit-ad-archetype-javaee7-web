package org.sitoolkit.ad.archetype.tips.infrastructure.code;

/**
 * この列挙型は、フラグの値を定義する{@link Code}です。
 *
 */
public enum FlagCD implements Code {
    No, Yes;

    @Override
    public String getLabel() {
        return name();
    }

    @Override
    public String getValue() {
        return Integer.toString(getFlag());
    }

    public short getFlag() {
        return (short) ordinal();
    }

    public static short toFlag(boolean boo) {
        return boo ? Yes.getFlag() : No.getFlag();
    }

    public static boolean toBoolean(int flag) {
        return flag == Yes.getFlag();
    }

}
