package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SearchConditionDoTest {

    /**
     * 以下の順に検索条件を追加し、WHERE節が期待通りに構築されることを確認します。
     * <ol>
     * <li>検索条件を設定する。 field1 = '1'
     * <li>WHERE節が構築できることを確認する。 entity.field1 = :field1
     * <li>のクエリパラメーターが追加されていることを確認する。 field1 = '1'
     * <li>検索条件を追加する。field2 in ('2', 'two')
     * <li>WHERE節が取得できることを確認する。 entity.field1 = :field1 AND field2 IN(:field2_1,
     * :field2_2)
     * <li>クエリパラメーターが追加されていることを確認する。 field2_1 = '2', field2_2 = 'two'
     * </ol>
     */
    @Test
    public void testBuildWhere() {
        SearchConditionDo condition = new SearchConditionDo();
        Map<String, Object> params = new HashMap<>();

        // 検索条件の設定
        params.put("eq_field1", "1");

        condition.setParams(params);

        assertThat("1条件 equals", condition.buildWhere("entity"), is("entity.field1 = :field1"));
        assertThat("クエリパラメーター", condition.getQueryParams().get("field1"), is("1"));

        // 検索条件の追加
        params.put("in_field2", new String[] { "2", "two" });

        assertThat("2条件 equals,in", condition.buildWhere("entity"),
                is("entity.field1 = :field1 AND entity.field2 IN (:field2_1, :field2_2)"));
        assertThat("クエリパラメーター1", condition.getQueryParams().get("field2_1"), is("2"));
        assertThat("クエリパラメーター2", condition.getQueryParams().get("field2_2"), is("two"));

    }

    /**
     * 以下の順に、ソートフィールドを追加しORDER BY節が期待どおりに構築されることを確認します。
     * <ol>
     * <li>インスタンス生成直後 -> ORDER BY節は空文字である
     * <li>field1をソートフィールドに追加する。-> ORDER BY entity.field1 ASC
     * <li>field1をソートフィールドに追加する。-> ORDER BY entity.field1 DESC
     * <li>field2をソートフィールドに追加する。-> ORDER BY entity.field2 ASC, entity.field1
     * DESC
     * <li>field2をソートフィールドに追加する。-> ORDER BY entity.field2 DESC, entity.field1
     * DESC
     * </ol>
     */
    @Test
    public void testOrderBy() {
        SearchConditionDo condition = new SearchConditionDo();
        assertThat("初期状態", condition.buildOrderBy("entity"), is(""));

        condition.setNewSortField("field1");
        assertThat("フィールド1追加", condition.buildOrderBy("entity"), is(" ORDER BY entity.field1 ASC"));

        condition.setNewSortField("field1");
        assertThat("フィールド1逆転", condition.buildOrderBy("entity"),
                is(" ORDER BY entity.field1 DESC"));

        condition.setNewSortField("field2");
        assertThat("フィールド2追加", condition.buildOrderBy("entity"),
                is(" ORDER BY entity.field2 ASC, entity.field1 DESC"));

        condition.setNewSortField("field2");
        assertThat("フィールド2逆転", condition.buildOrderBy("entity"),
                is(" ORDER BY entity.field2 DESC, entity.field1 DESC"));

        condition.setNewSortField("field1");
        assertThat("フィールド1追加", condition.buildOrderBy("entity"),
                is(" ORDER BY entity.field1 ASC, entity.field2 DESC"));

        condition.setNewSortField("field3");
        condition.setNewSortField("field4");
        assertThat("フィールド1追加", condition.buildOrderBy("entity"),
                is(" ORDER BY entity.field4 ASC, entity.field3 ASC, entity.field1 ASC"));

    }

    /**
     * インスタンス生成直後と検索条件追加後で、 カウントクエリと検索クエリの文字列が期待通りに構築されることを確認します。
     * 
     * <p>
     * 
     * インスタンス生成直後
     * <ul>
     * <li>SELECT COUNT(e) FROM Entity e
     * <li>SELECT e FROM Entity e
     * </ul>
     * 検索条件 field1 = '1'を追加後
     * <ul>
     * <li>SELECT COUNT(e) FROM Entity e WHERE e.field1 = :field1
     * <li>SELECT e FROM Entity e WHERE e.field1 = :field1
     * </ul>
     */
    @Test
    public void testBuildQuery() {
        SearchConditionDo condition = new SearchConditionDo();

        condition.buildQuery("Entity");
        assertThat("0条件 count", condition.getCountQuery(), is("SELECT COUNT(e) FROM Entity e"));
        assertThat("0条件 select", condition.getSearchQuery(), is("SELECT e FROM Entity e"));

        condition.addParam("eq_field1", "1");

        condition.buildQuery("Entity");
        assertThat("1条件 count", condition.getCountQuery(),
                is("SELECT COUNT(e) FROM Entity e WHERE e.field1 = :field1"));
        assertThat("1条件 select", condition.getSearchQuery(),
                is("SELECT e FROM Entity e WHERE e.field1 = :field1"));

    }
}
