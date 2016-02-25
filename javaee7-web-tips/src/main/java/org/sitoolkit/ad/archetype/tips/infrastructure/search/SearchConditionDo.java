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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(SearchConditionDo.class);

    /**
     * ページ制御DO
     */
    private PageControlDo pageCtrl = new PageControlDo();

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

    public PageControlDo getPageCtrl() {
        return pageCtrl;
    }

    public void setPageCtrl(PageControlDo pageCtrl) {
        this.pageCtrl = pageCtrl;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void init() {
        searchQuery = "";
        countQuery = "";
        queryParams.clear();
        pageCtrl.setCurrentPageNum(1);
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
        search.append("SELECT e FROM ").append(entityName).append(" e ");
        String where = buildWhere("e");
        if (where.length() > 0) {
            search.append("WHERE ").append(where);
        }
        search.append(buildOrderBy("e"));
        setSearchQuery(search.toString());

        StringBuilder count = new StringBuilder();
        count.append("SELECT COUNT(e) FROM ").append(entityName).append(" e ");
        if (where.length() > 0) {
            count.append(" WHERE ").append(where);
        }
        setCountQuery(count.toString());
    }

    /**
     *
     * @param shortName
     * @return
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
            String[] values = param.getKey().split("_");
            String opeStr = values[0].toLowerCase();
            String field = values[1];
            OperatorVo ope = OperatorVo.valueOf(opeStr);

            sb.append(shortName).append(".");
            sb.append(field).append(" ").append(ope).append(" ");

            if (OperatorVo.in.equals(ope)) {
                StringBuilder inSb = new StringBuilder();
                String[] inValues = (String[]) param.getValue();
                int i = 0;
                for (String inValue : inValues) {
                    if (inSb.length() > 0) {
                        inSb.append(",");
                    }
                    String placeHolder = field + i++;
                    inSb.append(":").append(placeHolder);
                    queryParams.put(placeHolder, inValue);
                }
                sb.append("(").append(inSb).append(")");
            } else {
                sb.append(" :");
                sb.append(field);

                switch (ope) {
                case sw:
                    value = value + "%";
                    break;
                default:
                }

                queryParams.put(field, value);
            }

        }

        return sb.toString();
    }

    /**
     * ORDER BY節を構築します。
     * 
     * @param sortName
     * @return
     */
    protected String buildOrderBy(String sortName) {
        if (sortList.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (SortDo sort : sortList) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(sortName).append(".").append(sort.getField()).append(" ");
            sb.append(sort.isAsc() ? "ASC" : "DESC");
        }
        sb.insert(0, " ORDER BY ");
        return sb.toString();
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

    public int getSortHistory() {
        return sortHistory;
    }

    public void setSortHistory(int sortHistory) {
        this.sortHistory = sortHistory;
    }
}
