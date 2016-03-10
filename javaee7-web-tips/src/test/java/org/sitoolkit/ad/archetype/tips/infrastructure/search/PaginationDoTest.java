package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PaginationDoTest {

    /**
     * 実際のユースケースに即したケースです。 以下の設定値、入力値をプロパティに設定し、期待する演算結果が得られることを確認します。
     * <p>
     * 設定値
     * <ul>
     * <li>表示ページ番号数:5
     * <li>1ページあたり行数:50
     * </ul>
     * 入力値
     * <ul>
     * <li>現在のページ番号:1
     * <li>検索結果の行数:100
     * </ul>
     * 演算結果
     * <ul>
     * <li>検索結果開始行番号:0
     * <li>検索結果終了行番号:49
     * <li>終了ページ判定:false
     * </ul>
     */
    @Test
    public void testUsecase() {
        PaginationDo pagination = new PaginationDo(5, 50);

        pagination.setCurrentPageNum(1);
        assertThat("有効判定 no", pagination.isEnabled(), is(false));
        pagination.setTotalRowCnt(100);
        assertThat("有効判定 yes", pagination.isEnabled(), is(true));

        assertThat("検索結果開始行番号", pagination.getRowNumFrom(), is(0));
        assertThat("検索結果終了行番号", pagination.getRowNumTo(), is(49));
        assertThat("ページ番号リスト", pagination.getPageNums(), is(contains(1, 2)));
        assertThat("ページ番号リスト 2回目", pagination.getPageNums(), is(contains(1, 2)));

    }

    /**
     * 最終ページ判定メソッド({@link PaginationDo#isLastPage()})のケースです。
     * 以下の設定値、入力値に対しメソッドの値が期待値通りとなることを確認します。
     * <p>
     * 設定値、入力値
     * <ul>
     * <li>表示ページ番号数:5
     * <li>1ページあたり行数:50
     * <li>検索結果の行数:100
     * </ul>
     * 
     * 期待値(現在のページ番号:isLastPage)
     * <ul>
     * <li>0:true
     * <li>1:false
     * <li>2:true
     * </ul>
     */
    @Test
    public void testIsLastPage() {
        PaginationDo pagination = new PaginationDo(5, 50);
        assertThat("最終ページ判定 default", pagination.isLastPage(), is(true));

        pagination.setCurrentPageNum(1);
        pagination.setTotalRowCnt(100);

        assertThat("最終ページ判定 no", pagination.isLastPage(), is(false));

        pagination.setCurrentPageNum(2);
        assertThat("最終ページ判定 yes", pagination.isLastPage(), is(true));

    }

    /**
     * 有効性判定メソッド({@link PaginationDo#isEnabled()})のケースです。
     * <p>
     * コンディション : 期待値
     * <ul>
     * <li>初期状態 : false
     * <li>検索結果の行数>=0 : true
     * </ul>
     */
    @Test
    public void testIsActive() {
        PaginationDo pagination = new PaginationDo(5, 50);

        assertThat("有効判定 no", pagination.isEnabled(), is(false));

        pagination.setTotalRowCnt(0);
        assertThat("有効判定 yes", pagination.isEnabled(), is(true));

    }
}
