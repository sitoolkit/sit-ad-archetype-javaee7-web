package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 * このクラスは、検索画面で入力される検索条件を格納するDOです。
 *
 * @author SIToolkit
 */
@Named
@ConversationScoped
public class SearchConditionDo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ページ制御DO
     */
    private PaginationDo pagination = new PaginationDo();

    /**
     * 画面入力された検索条件のマップ キー：演算子 + "_" + 検索フィールド、値：検索に使用する値
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * クエリのパラメータとなるマップ キー：クエリのパラメータのプレースホルダ、値：プレースホルダに対応する値
     */
    private Map<String, Object> queryParams = new HashMap<String, Object>();

    /**
     * ソートキーのリスト
     */
    private List<SortDo> sortList = new ArrayList<SortDo>();

    /**
     * 検索結果件数を数えるためのクエリ
     */
    private String countQuery;

    /**
     * 検索結果を取得するためのクエリ
     */
    private String searchQuery;

    /**
     * ソートキーを履歴として保持する数
     */
    private int sortHistory = 3;

    /**
     * 検索条件を初期化します。
     */
    public void init() {
        searchQuery = "";
        countQuery = "";
        queryParams.clear();
        pagination.setCurrentPageNum(1);
        sortList.clear();
    }

    /**
     * searchCondition、pageCtrl、sortListを基に searchQuery、countQueryの文字列を構築する。
     * 
     * @param entityName
     *            エンティティ名
     */
    public void buildQuery(String entityName) {
        StringBuilder search = new StringBuilder();
        search.append("SELECT e FROM ").append(entityName).append(" e");
        String where = buildWhere("e");
        if (where.length() > 0) {
            search.append(" WHERE ").append(where);
        }
        search.append(buildOrderBy("e"));
        setSearchQuery(search.toString());

        StringBuilder count = new StringBuilder();
        count.append("SELECT COUNT(e) FROM ").append(entityName).append(" e");
        if (where.length() > 0) {
            count.append(" WHERE ").append(where);
        }
        setCountQuery(count.toString());
    }

    /**
     * WHERE節を構築します。
     * 
     * @param shortName
     *            WHERE節の中でフィールドの接頭辞として使用するエンティティの省略名
     * @return WHERE節
     */
    protected String buildWhere(String shortName) {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, Object> param : getParams().entrySet()) {
            Object value = param.getValue();
            if (value == null || value.toString().isEmpty()
                    || (value.getClass().isArray() && Array.getLength(value) == 0)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(" AND ");
            }
            String[] operatorAndField = param.getKey().split("_");

            if (operatorAndField.length != 2) {
                throw new IllegalArgumentException("Map paramsに不正なキー:" + param.getKey()
                        + "が含まれています。" + "キーは「OperatorCd + \"_\" + endityField」で指定してください。");
            }
            String opeStr = operatorAndField[0].toUpperCase();
            String field = operatorAndField[1];
            OperatorCd ope = OperatorCd.valueOf(opeStr);

            sb.append(shortName).append(".");
            sb.append(field).append(" ").append(ope).append(" ");

            if (OperatorCd.IN.equals(ope)) {
                StringBuilder inSb = new StringBuilder();
                String[] inValues = (String[]) param.getValue();
                int paramSufix = 1;
                for (String inValue : inValues) {
                    if (inSb.length() > 0) {
                        inSb.append(", ");
                    }
                    String placeHolder = field + "_" + paramSufix++;
                    inSb.append(":").append(placeHolder);
                    queryParams.put(placeHolder, inValue);
                }
                sb.append("(").append(inSb).append(")");
            } else {
                sb.append(":");
                sb.append(field);
                queryParams.put(field, value);
            }

        }

        return sb.toString();
    }

    /**
     * ORDER BY節を構築します。
     * 
     * @param shortName
     *            ORDER BY節の中でフィールドの接頭辞として使用するエンティティの省略名
     * @return ORDER BY節
     */
    protected String buildOrderBy(String shortName) {
        if (sortList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (SortDo sort : sortList) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(shortName).append(".").append(sort.getField()).append(" ");
            sb.append(sort.isAsc() ? "ASC" : "DESC");
        }
        sb.insert(0, " ORDER BY ");
        return sb.toString();
    }

    /**
     * ソートキーのリストの先頭にソートキーを追加します。 追加するキーがリスト内の最初のキーと同じ場合、
     * キーの追加は行わずリスト内の最初のキーの昇順・降順を反転します。 追加するキーが既にリスト内に存在する場合、 既存のキーをリストから除きます。
     * リストのサイズがソート履歴件数以上である場合、 リスト内の
     *
     * @param field
     *            追加するソートキー
     */
    public void setNewSortField(String field) {
        if (sortList.isEmpty()) {
            SortDo sort = new SortDo(field, true);
            sortList.add(sort);
        } else {
            SortDo first = sortList.get(0);
            if (first.getField().equals(field)) {
                first.setAsc(!first.isAsc());
            } else {
                SortDo same = null;
                for (SortDo history : sortList) {
                    if (history.getField().equals(field)) {
                        same = history;
                    }
                }
                sortList.remove(same);
                if (sortList.size() >= getSortHistory()) {
                    sortList.remove(sortList.size() - 1);
                }
                SortDo newFirst = new SortDo(field, true);
                sortList.add(0, newFirst);
            }
        }
    }

    public Object addParam(String key, Object value) {
        return params.put(key, value);
    }

    public PaginationDo getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDo pagination) {
        this.pagination = pagination;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getSortHistory() {
        return sortHistory;
    }

    public void setSortHistory(int sortHistory) {
        this.sortHistory = sortHistory;
    }

    public String getCountQuery() {
        return countQuery;
    }

    public void setCountQuery(String countQuery) {
        this.countQuery = countQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

}
