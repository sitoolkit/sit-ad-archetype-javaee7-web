#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain.code;

import ${package}.infrastructure.code.Code;

/**
 * 
 **/
public enum 性別CD implements Code {
    男性("1"), 女性("2"),;

    private 性別CD(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return name();
    }

    @Override
    public String toString() {
        return getLabel();
    }

}
