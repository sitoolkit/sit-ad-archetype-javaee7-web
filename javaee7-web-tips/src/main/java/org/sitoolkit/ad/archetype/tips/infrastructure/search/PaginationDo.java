package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * このクラスはページネーション処理の各種パラメーターを演算するDOです。
 * <p>
 * このクラスを使用したページネーションのUIは例えば以下の様なものです。
 * 
 * <pre>
 *
 * <u>&lt;&lt;</u> <u>&lt;</u> <u>3</u> 4 <u>5</u> <u>6</u> <u>7</u> <u>&gt;</u> <u>&gt;&gt;</u>
 *
 * </pre>
 * 
 * 並んだ数字3～7は、ページ番号を表します。 下線付きの数字はリンクを表し、下線無しは現在表示しているページ番号を表します。
 * 
 * <h3>使用方法</h3> 主要なプロパティとそれらを使用するタイミングを説明します。
 * 以下のプロパティは検索画面の共通仕様としてアプリや画面ごとに決まった値を設定します。
 * 
 * <ul>
 * <li>pageNumCount : UIに表示するページ番号の数です。上記の例では「５」が設定されています。
 * <li>rowCntPerPage : 1ページに表示する検索結果の行数の上限です。
 * </ul>
 * 
 * 以下のプロパティは1回の検索処理ごとに設定します。
 * <ul>
 * <li>currentPageNum : UIでクリックされたページ番号を設定します。上記の例では「4」が設定されています。
 * <li>totalRowCnt : 検索条件に一致したDBのレコードの件数を設定します。
 * </ul>
 * 
 * 上記のプロパティが設定されると、以下の演算結果が取得できるようになります。
 * <ul>
 * <li>rowNumFrom : 検索結果としてDBから抽出するレコードの開始位置を返します。番号は0から数えます。
 * この値はjavax.persistence.Query.setFirstResult(int)に指定することを想定しています。
 * <li>pageNums : UIに表示するページ番号のリストを返します上記の例では[3, 4, 5, 6, 7]です。
 * </ul>
 *
 * @author SIToolkit
 */
public class PaginationDo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7895288898589369244L;

    /**
     * 現在のページ番号 1から開始
     */
    private int currentPageNum = 1;
    /**
     * 1ページあたりに表示される要素の件数
     */
    private int rowCntPerPage = 30;
    /**
     * 全行数
     */
    private int totalRowCnt = -1;
    /**
     * ページ番号数
     */
    private int pageNumCnt = 5;
    /**
     * ページ番号のリスト
     */
    private List<Integer> pages;

    /**
     * デフォルトコンストラクタです。特に処理は行いません。
     */
    public PaginationDo() {
        super();
    }

    /**
     * 現在のページ番号と1ページ当たりの行数を設定してインスタンスを生成します。
     * 
     * @param pageNumCount
     *            現在のページ数
     * @param rowCntPerPage
     *            1ページ当たりの行数
     */
    public PaginationDo(int pageNumCount, int rowCntPerPage) {
        this();
        this.setPageNumCnt(pageNumCount);
        this.setRowCntPerPage(rowCntPerPage);
    }

    /**
     * 現在のページ番号を返します。
     * 
     * @return 現在のページ番号
     */
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     * 現在のページ番号を設定します。
     * 
     * @param currentPageNum
     *            現在のページ番号
     */
    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
        setPageNums(null);
    }

    /**
     * 1ページ当たりの行数を返します。
     * 
     * @return 1ページ当たりの行数
     */
    public int getRowCntPerPage() {
        return rowCntPerPage;
    }

    /**
     * 1ページ当たりの行数を設定します。
     * 
     * @param rowCntPerPage
     *            1ページ当たりの行数
     */
    public void setRowCntPerPage(int rowCntPerPage) {
        this.rowCntPerPage = rowCntPerPage;
        setPageNums(null);
    }

    /**
     * 開始行番号を返します。
     * 
     * @return 開始行番号
     */
    public int getRowNumFrom() {
        return (getCurrentPageNum() - 1) * getRowCntPerPage();
    }

    /**
     * 終了行番号を返します。
     * 
     * @return 終了行番号
     */
    public int getRowNumTo() {
        return Math.min(getRowNumFrom() + getRowCntPerPage() - 1, getTotalRowCnt());
    }

    /**
     * 開始ページ番号を返します。
     * 
     * @return 開始ページ番号
     */
    public int getPageNumFrom() {
        return Math.max(1, Math.min(getCurrentPageNum() - (getPageNumCnt() / 2),
                getLastPageNum() - getPageNumCnt() + 1));
    }

    /**
     * 終了ページ番号を返します。
     * 
     * @return 終了ページ番号
     */
    public int getPageNumTo() {
        return Math.min(getLastPageNum(), getPageNumFrom() + getPageNumCnt() - 1);
    }

    /**
     * 最後のページ番号を返します。
     * 
     * @return 最後のページ番号
     */
    public int getLastPageNum() {
        return (getTotalRowCnt() - 1) / getRowCntPerPage() + 1;
    }

    /**
     * 現在のページが最後のページであるか否かを判定します。
     * 
     * @return true:最後のページである。
     */
    public boolean isLastPage() {
        return getCurrentPageNum() == 0 ? true : getLastPageNum() == getCurrentPageNum();
    }

    /**
     * 総行数を返します。
     * 
     * @return 総行数
     */
    public int getTotalRowCnt() {
        return totalRowCnt;
    }

    /**
     * 全行数を設定します。
     * 
     * @param totalRowCnt
     *            全行数
     */
    public void setTotalRowCnt(int totalRowCnt) {
        this.totalRowCnt = totalRowCnt;
        setPageNums(null);
    }

    /**
     * ページ番号数を返します。 初期値は5です。
     * 
     * @return ページ番号数
     */
    public int getPageNumCnt() {
        return pageNumCnt;
    }

    /**
     * ページ番号数を設定します。
     * 
     * @param pageNumCnt
     *            ページ番号数
     */
    public void setPageNumCnt(int pageNumCnt) {
        this.pageNumCnt = pageNumCnt;
        setPageNums(null);
    }

    /**
     * ページ番号のリストを返します。 このリストの要素は、開始ページ番号から終了ページ番号までの連続する整数です。
     * 
     * @return ページ番号のリスト
     */
    public List<Integer> getPageNums() {
        if (pages == null) {
            pages = new ArrayList<Integer>();
            final int toPage = getPageNumTo();
            for (int i = getPageNumFrom(); i <= toPage; i++) {
                pages.add(i);
            }
        }
        return pages;
    }

    private void setPageNums(List<Integer> pageNums) {
        this.pages = pageNums;
    }

    public boolean isEnabled() {
        return getTotalRowCnt() >= 0;
    }
}
