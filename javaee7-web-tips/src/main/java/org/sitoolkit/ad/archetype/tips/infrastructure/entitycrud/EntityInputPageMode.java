package org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud;

/**
 * この列挙型は、マスターメンテナンス入力画面のモードを表します。
 *
 * @author SIToolkit
 */
public enum EntityInputPageMode {
    /**
     * 作成モードです。
     */
    CREATE,
    /**
     * 更新モードです。
     */
    UPDATE;

    /**
     * 作成モードである場合にtrueを返します。
     * 
     * @return 作成モードである場合にtrue
     */
    public boolean isCreate() {
        return CREATE.equals(this);
    }

    /**
     * 更新モードである場合にtrueを返します。
     * 
     * @return 更新モードである場合にtrue
     */
    public boolean isUpdate() {
        return UPDATE.equals(this);
    }

    public boolean isAny() {
        return true;
    }

}
