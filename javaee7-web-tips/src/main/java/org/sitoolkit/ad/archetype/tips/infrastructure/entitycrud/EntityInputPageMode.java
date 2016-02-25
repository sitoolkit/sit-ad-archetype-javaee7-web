package org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud;

/**
 * この列挙型は、マスターメンテナンス入力画面のモードを表します。
 *
 * @author SIToolkit
 */
public enum EntityInputPageMode {
    /**
     * 作成モード
     */
    create,
    /**
     * 更新モード
     */
    update;

    /**
     * 作成モードである場合にtrueを返します。
     * 
     * @return 作成モードである場合にtrue
     */
    public boolean isCreate() {
        return create.equals(this);
    }

    /**
     * 更新モードである場合にtrueを返します。
     * 
     * @return 更新モードである場合にtrue
     */
    public boolean isUpdate() {
        return update.equals(this);
    }

    public boolean isAny() {
        return true;
    }

}
