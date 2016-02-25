#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.presentation;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.application.user.UserService;
import ${package}.domain.code.性別CD;
import ${package}.domain.user.UserEntity;
import ${package}.infrastructure.entitycrud.EntityInputController;

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
