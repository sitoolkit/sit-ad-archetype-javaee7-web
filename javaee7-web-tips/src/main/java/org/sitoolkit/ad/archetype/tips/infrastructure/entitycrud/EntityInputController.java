package org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud;

import java.io.Serializable;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.transaction.RollbackException;
import javax.validation.Valid;

import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.presentation.jsf.JSFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * このクラスは、エンティティ入力画面のモデルが継承する基底クラスです。
 * 
 */
public abstract class EntityInputController<E extends BaseEntity, I extends Serializable, S extends BaseService<E, I>>
        implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ロガー
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * CRUD対象のエンティティ
     */
    @Valid
    private E entity;

    /**
     * 画面のモード
     */
    private EntityInputPageMode mode;

    public EntityInputController() {
        super();
    }

    /**
     * 初期化処理を行います。
     * <ol>
     * <li>画面のモードを決定します。
     *
     * <ul>
     * <li>エンティティIdが取得できた場合、更新モード
     * <li>それ以外の場合、作成モード
     * </ul>
     *
     * <li>更新モードの場合、エンティティIdをキーにDBからエンティティを取得し、 処理対象のエンティティとします。
     * <li>作成モードの場合、エンティティのインスタンスを生成し、 処理対象のエンティティとします。
     * </ol>
     */
    public void init() {
        if (getEntityId() == null || "".equals(getEntityId())) {
            setMode(EntityInputPageMode.create);
        } else {
            setMode(EntityInputPageMode.update);
        }
        if (getMode().isCreate()) {
            setEntity(getService().newInstance());
        } else {
            setEntity(getService().find(getEntityId()));
        }
    }

    /**
     * 処理対象のエンティティをDBに保存します。
     * 
     * @return 一覧画面のビューId
     */
    public String create() {
        try {
            getService().create(getEntity());
            JSFUtils.info("登録しました。", getEntityId());
        } catch (EJBException e) {
            if (e.getCause() instanceof RollbackException) {
                JSFUtils.error("登録時にエラーが発生しました。");
                log.error("エラー内容：", e);
            }
        }
        return JSFUtils.redirect(getListPageViewId());
    }

    /**
     * 処理対象のエンティティでDBを更新します。
     * 
     * @return 一覧画面のViewId
     */
    public String update() {
        try {
            getService().update(getEntity());
            JSFUtils.info("{0}を更新しました。", getEntityId());
        } catch (EJBException e) {
            if (e.getCause() instanceof OptimisticLockException) {
                JSFUtils.error("{0}は他のユーザーによって更新されています。", getEntityId());
            } else {
                throw e;
            }
        }
        return JSFUtils.redirect(getListPageViewId());
    }

    /**
     * 処理対象のエンティティを論理削除します。
     * 
     * @return 一覧画面のViewId
     */
    public String delete() {
        getService().delete(getEntityId());
        JSFUtils.info("{0}を削除しました。", getEntityId());
        return JSFUtils.redirect(getListPageViewId());
    }

    /**
     * 具象モデルが所属するドメインのサービスを取得します。
     * 
     * @return 当該モデルが所属するドメインのサービス
     */
    protected abstract S getService();

    /**
     * 処理対象のエンティティのIDを取得します。
     * 
     * @return 処理対象のエンティティのID
     */
    protected abstract I getEntityId();

    /**
     * 一覧画面のViewIdを取得します。
     * 
     * @return 一覧画面のViewId
     */
    protected String getListPageViewId() {
        return getDomain() + "-list";
    }

    protected String getDomain() {
        String domain = getClass().getSimpleName().replace("InputController", "");
        return domain.substring(0, 1).toLowerCase() + domain.substring(1, domain.length());
    }

    /**
     * 画面のモードを取得します。
     * 
     * @return 画面のモード
     */
    public EntityInputPageMode getMode() {
        return mode;
    }

    /**
     * 画面のモードを設定します。
     * 
     * @param mode
     *            画面のモード
     */
    public void setMode(EntityInputPageMode mode) {
        this.mode = mode;
    }

    /**
     * 処理対象のエンティティを取得します。
     * 
     * @return 処理対象のエンティティ
     */
    public E getEntity() {
        return entity;
    }

    /**
     * 処理対象のエンティティを設定します。
     * 
     * @param entity
     *            処理対象のエンティティ
     */
    public void setEntity(E entity) {
        this.entity = entity;
    }

}
