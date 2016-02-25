package org.sitoolkit.ad.archetype.tips.presentation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.sitoolkit.ad.archetype.tips.application.user.UserService;
import org.sitoolkit.ad.archetype.tips.domain.code.性別CD;
import org.sitoolkit.ad.archetype.tips.domain.user.UserEntity;
import org.sitoolkit.ad.archetype.tips.infrastructure.entitycrud.EntityInputController;

@Named
@ViewScoped
public class UserInputController extends EntityInputController<UserEntity, String, UserService>
        implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ユーザーID
     */
    private String userId;

    @Inject
    UserService service;

    public 性別CD[] get性別() {
        return 性別CD.values();
    }

    @Override
    protected UserService getService() {
        return service;
    }

    @Override
    protected String getEntityId() {
        return getUserId();
    }

    /**
     * ユーザーIDを返却する。
     * 
     * @return ユーザーID
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * ユーザーIDを設定する。
     * 
     * @param userId
     *            ユーザーID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
