package org.sitoolkit.ad.archetype.tips.infrastructure.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * このクラスは、ページ制御のためのDOです。
 * 「ページ制御」とは、ユーザーの画面操作によってデータベースから抽出する
 * エンティティの件数、開始行番号、終了行番号件数を制御することを指します。
 *
 * <br/>
 * このクラスで実装されるページ制御の画面は以下のようなものを想定しています。
 *
 *
 * <pre>
 *
 * &lt;&lt; &lt; <u>3</u> <u>4</u> 5 <u>6</u> <u>7</u> &gt; &gt;&gt;
 *
 * </pre>
 * 並んだ数字3～7は、ページ番号を表します。
 * 下線付きの数字はリンクを表し、下線無しは現在表示しているページ番号を表します。
 *
 * @author SIToolkit
 */
public class PageControlDo implements Serializable {

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
    public PageControlDo() {
        super();
    }

    /**
     * 現在のページ番号と1ページ当たりの行数を設定してインスタンスを生成します。
     * @param currentPage 現在のページ数
     * @param rowCntPerPage 1ページ当たりの行数
     */
    public PageControlDo(int currentPage, int rowCntPerPage) {
        super();
        this.currentPageNum = currentPage;
        this.rowCntPerPage = rowCntPerPage;
    }

    /**
     * 現在のページ番号を返します。
     * @return 現在のページ番号
     */
    public int getCurrentPageNum() {
        return currentPageNum;
    }

    /**
     * 現在のページ番号を設定します。
     * @param currentPageNum 現在のページ番号
     */
    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
        setPageNums(null);
    }

    /**
     * 1ページ当たりの行数を返します。
     * @return 1ページ当たりの行数
     */
    public int getRowCntPerPage() {
        return rowCntPerPage;
    }

    /**
     * 1ページ当たりの行数を設定します。
     * @param rowCntPerPage 1ページ当たりの行数
     */
    public void setRowCntPerPage(int rowCntPerPage) {
        this.rowCntPerPage = rowCntPerPage;
        setPageNums(null);
    }

    /**
     * 開始行番号を返します。
     * @return 開始行番号
     */
    public int getRowNumFrom() {
        return (getCurrentPageNum() - 1) * getRowCntPerPage();
    }

    /**
     * 終了行番号を返します。
     * @return 終了行番号
     */
    public int getRowNumTo() {
        return Math.min(getRowNumFrom() + getRowCntPerPage() - 1, getTotalRowCnt());
    }

    /**
     * 開始ページ番号を返します。
     * @return 開始ページ番号
     */
    public int getPageNumFrom() {
        return Math.max(1, Math.min(getCurrentPageNum() - ( getPageNumCnt() / 2 ), getLastPageNum() - getPageNumCnt() + 1));
    }

    /**
     * 終了ページ番号を返します。
     * @return 終了ページ番号
     */
    public int getPageNumTo() {
        return Math.min(getLastPageNum(), getPageNumFrom() + getPageNumCnt() - 1);
    }

    /**
     * 最後のページ番号を返します。
     * @return 最後のページ番号
     */
    public int getLastPageNum() {
        return ( getTotalRowCnt() - 1 ) / getRowCntPerPage() + 1;
    }

    /**
     * 現在のページが最後のページであるか否かを判定します。
     * @return true:最後のページである。
     */
    public boolean isLastPage() {
        return getCurrentPageNum() == 0 ? true : getLastPageNum() == getCurrentPageNum();
    }

    /**
     * 全行数を返します。
     * @return 全行数
     */
    public int getTotalRowCnt() {
        return totalRowCnt;
    }

    /**
     * 全行数を設定します。
     * @param totalRowCnt 全行数
     */
    public void setTotalRowCnt(int totalRowCnt) {
        this.totalRowCnt = totalRowCnt;
        setPageNums(null);
    }

    /**
     * ページ番号数を返します。
     * 初期値は5です。
     * @return ページ番号数
     */
    public int getPageNumCnt() {
        return pageNumCnt;
    }

    /**
     * ページ番号数を設定します。
     * @param pageNumCnt ページ番号数
     */
    public void setPageNumCnt(int pageNumCnt) {
        this.pageNumCnt = pageNumCnt;
        setPageNums(null);
    }

    /**
     * ページ番号のリストを返します。
     * このリストの要素は、開始ページ番号から終了ページ番号までの連続する整数です。
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
