package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import java.io.Serializable;

/**
 * このクラスは、検索結果のソート順を格納するためのDOです。
 * 
 * @author SIToolkit
 */
public class SortDo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7479300847234641010L;

    private String field;

    private boolean asc;

    public SortDo() {
    }

    public SortDo(String field, boolean asc) {
        this.field = field;
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
