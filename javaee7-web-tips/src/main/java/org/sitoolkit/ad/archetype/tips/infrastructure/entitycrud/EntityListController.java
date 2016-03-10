package org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.sitoolkit.ad.archetype.tips.infrastructure.code.FlagCd;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.presentation.jsf.JsfUtils;
import org.sitoolkit.ad.archetype.tips.infrastructure.search.SearchConditionDo;

/**
 * このクラスは、エンティティ一覧画面の機能を実装するコントローラーです。
 * 
 * @author SIToolkit
 */
public abstract class EntityListController<E extends BaseEntity, I extends Serializable, S extends BaseService<E, I>> {

    /**
     * CDIのカンバセーションオブジェクトです。
     */
    @Inject
    protected Conversation conversation;

    /**
     * 画面入力を保持し検索処理に使用するDOです。
     */
    @Inject
    protected SearchConditionDo condition;

    /**
     * 検索結果
     */
    private List<E> list;

    /**
     * 具象モデルが所属するドメインのサービスを取得します。
     * 
     * @return 当該モデルが所属するドメインのサービス
     */
    protected abstract S getService();

    /**
     * 検索条件をもとに検索処理を行い、結果を内部保持します。
     * 
     * @return null
     */
    public String search() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        conversation.begin();
        getCondition().init();
        getCondition().getParams().put("eq_deletedFlg", FlagCd.No.getFlag());

        setList(getService().search(getCondition()));

        if (getList().isEmpty()) {
            JsfUtils.info("検索結果はありません。条件を変えて再検索してください。");
        }
        return null;
    }

    /**
     * カンバセーションを終了し、 検索条件、検索結果をクリアします。
     * 
     * @return null
     */
    public String clear() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        getCondition().init();
        getCondition().getParams().clear();
        setList(null);
        return null;
    }

    /**
     * ページ番号を変更して再検索します。
     * 
     * @param pageNum
     *            ページ番号
     * 
     * @return null
     */
    public String goToPage(int pageNum) {
        getCondition().getPagination().setCurrentPageNum(pageNum);
        setList(getService().search(getCondition()));
        return null;
    }

    /**
     * ソートキーを追加して再検索します。
     * 
     * @param field
     *            ソートキーに追加するエンティティのフィールド名
     * 
     * @return null
     */
    public String sort(String field) {
        getCondition().setNewSortField(field);
        setList(getService().search(getCondition()));
        return null;
    }

    /**
     * 初期化処理を行います。
     * <ul>
     * <li>カンバセーションが開始されている場合、 カンバセーション内の検索条件で検索処理を行います。
     *
     * <li>それ以外の場合、何も処理を行いません。
     * </ul>
     */
    @PostConstruct
    public void init() {
        if (!conversation.isTransient()) {
            setList(getService().search(getCondition()));
        }
    }

    /**
     * 検索結果を取得します。
     * 
     * @return 検索結果
     */
    public List<E> getList() {
        return list;
    }

    /**
     * 検索結果を設定します。
     * 
     * @param list
     *            検索結果
     */
    public void setList(List<E> list) {
        this.list = list;
    }

    /**
     * 検索条件を取得します。
     * 
     * @return 検索条件
     */
    public SearchConditionDo getCondition() {
        return condition;
    }

    /**
     * 検索条件を設定します。
     * 
     * @param condition
     *            検索条件
     */
    public void setCondition(SearchConditionDo condition) {
        this.condition = condition;
    }

}
