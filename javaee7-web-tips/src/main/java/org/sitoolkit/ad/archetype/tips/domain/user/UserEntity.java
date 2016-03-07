package org.sitoolkit.ad.archetype.tips.domain.user;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.sitoolkit.ad.archetype.tips.domain.code.性別CD;
import org.sitoolkit.ad.archetype.tips.infrastructure.code.CodeUtils;
import org.sitoolkit.ad.archetype.tips.infrastructure.data.jpa.BaseEntityListener;

@Entity
@Table(name = "USER_ENTITY")
@EntityListeners(BaseEntityListener.class)
public class UserEntity extends BaseUserEntity {

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

    public 性別CD getGenderCd() {
        return CodeUtils.decode(getGender(), 性別CD.class);
    }

    public void setGenderCd(性別CD gender) {
        setGender(gender.getValue());
    }

}
