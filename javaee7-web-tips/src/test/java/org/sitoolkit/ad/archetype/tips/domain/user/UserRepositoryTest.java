package org.sitoolkit.ad.archetype.tips.domain.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jpa.impl.transaction.TransactionalInterceptor;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.deltaspike.SupportDeltaspikeJpa;
import org.jglue.cdiunit.internal.InRequestInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.TestEntityManagerProducer;

@SupportDeltaspikeJpa
@RunWith(CdiRunner.class)
@AdditionalClasses(TestEntityManagerProducer.class)
public class UserRepositoryTest {

    @Inject
    UserRepository repo;

    /*
     * CdiUnitのガイド通りに@InRequestと@Transactionalを使うと、以下の例外が発生します。
     * 
     * org.jboss.weld.context.ContextNotActiveException: WELD-001303: No active
     * contexts for scope type javax.enterprise.context.RequestScoped
     * 
     * これは、InRequestInterceptorよりもTransactionalInterceptorが先に処理されるにもかかわらず、
     * その処理の中でRequestScopeのBeanを参照するためです。
     * この問題を回避するため、@Interceptorsで明示的に処理順を指定する方法をとります。
     */
    @Interceptors({ InRequestInterceptor.class, TransactionalInterceptor.class })
    @Transactional(readOnly = true)
    // @InRequestScope
    @Test
    public void testSelectByLastName() {
        UserEntity user = UserFactory.create();
        user.setLastName(RandomStringUtils.randomAlphanumeric(32));

        repo.create(user);
        repo.em().flush();

        List<UserEntity> result = repo.selectByLastName(user.getLastName());

        assertThat("", result.get(0).getLastName(), is(user.getLastName()));
    }

}
