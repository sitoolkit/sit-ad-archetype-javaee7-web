package org.sitoolkit.ad.archetype.tips.domain.user;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.sitoolkit.ad.archetype.tips.domain.code.性別Cd;
import org.sitoolkit.ad.archetype.tips.infrastructure.code.CodeUtils;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseEntityListener;

@Entity
@Table(name = "USER_ENTITY")
@EntityListeners(BaseEntityListener.class)
@NamedQueries({
        @NamedQuery(name = UserEntity.SELECT_BY_LAST_NAME, query = "SELECT e FROM UserEntity e WHERE e.lastName = :lastName") })
public class UserEntity extends BaseUserEntity {

    private static final long serialVersionUID = -1548565565852076272L;

    static final String SELECT_BY_LAST_NAME = "UserEntity.selectByLastName";

    @Override
    @NotNull
    public String getUserId() {
        return super.getUserId();
    }

    @Override
    @NotNull
    public String getPassword() {
        return super.getPassword();
    }

    public 性別Cd getGenderCd() {
        return CodeUtils.decode(getGender(), 性別Cd.class);
    }

    public void setGenderCd(性別Cd gender) {
        setGender(gender.getValue());
    }

}
