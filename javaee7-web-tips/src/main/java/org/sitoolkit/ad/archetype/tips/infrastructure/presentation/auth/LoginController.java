package org.sitoolkit.ad.archetype.tips.infrastructure.presentation.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.sitoolkit.ad.archetype.tips.infrastructure.Controller;
import org.sitoolkit.ad.archetype.tips.infrastructure.presentation.jsf.JsfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * このクラスは、認証処理のファサードとなる{@link Controller}です。
 *
 */
@Named
@RequestScoped
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private String loginId;

    private String password;

    private String requestedViewId;

    @Inject
    private HttpServletRequest request;

    /**
     * 内部保持するログインID、パスワードを用いて認証を行います。 認証処理は{@link HttpServletRequest#login(String, String)}
     * メソッドに委譲します。
     *
     * @return 認証が成功し、かつ直接ログイン画面に遷移して認証を行っている場合は、ログイン画面のViewIdを返します。
     *         認証が成功し、かつログイン画面とは別の画面への遷移でログイン画面にフォワードされた場合は、
     *         元々遷移しようとしていた画面のViewIdを返します。 認証が失敗した場合はログイン画面のViewIdを返します。
     * @see HttpServletRequest#login(String, String)
     */
    public String login() {
        try {
            JsfUtils.req().login(getLoginId(), getPassword());
            JsfUtils.info("ログインしました。");

            if (StringUtils.isEmpty(getRequestedViewId())) {
                return JsfUtils.viewId();
            } else {
                return JsfUtils.redirect(getRequestedViewId());
            }
        } catch (ServletException e) {
            JsfUtils.error("ログインIDまたはパスワードが不正です。");
            return JsfUtils.viewId();
        }
    }

    /**
     * 認証済ユーザーのログアウト処理を行います。 処理は{@link HttpServletRequest#logout()}メソッドに委譲します。
     * ログアウト処理後は、コンテキストルート直下にリダイレクトします。
     *
     * @see JsfUtils#redirectHome()
     */
    public void logout() {
        try {
            request.logout();
            JsfUtils.info("ログアウトしました。");
        } catch (ServletException e) {
            LOG.error("ログアウト処理で例外が発生しました。", e);
        }
    }

    /**
     * ユーザーが認証済である場合にtrueを返します。
     *
     * @return ユーザーが認証済である場合にtrue
     */
    public boolean isLoggedIn() {
        return JsfUtils.ext().getRemoteUser() != null;
    }

    /**
     * 元々リクエストされたフォワード前のViewIdを返します。
     * 
     * @return 元々リクエストされたフォワード前のViewId
     */
    public String getRequestedViewId() {
        if (requestedViewId == null) {
            requestedViewId = JsfUtils.forwardSrcPath();
        }
        return requestedViewId;
    }

    /**
     * 元々リクエストされたフォワード前のViewIdを設定します。
     * 
     * @param requestedViewId
     *            元々リクエストされたフォワード前のViewId
     */
    public void setRequestedViewId(String requestedViewId) {
        this.requestedViewId = requestedViewId;
    }

    public String getLoginUserId() {
        return request.getRemoteUser();
    }

    /**
     * ログインIDを取得します。
     *
     * @return ログインID
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * ログインIDを設定します。
     *
     * @param loginId
     *            設定するログインID
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * パスワードを取得します。
     *
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定します。
     *
     * @param password
     *            設定するパスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
