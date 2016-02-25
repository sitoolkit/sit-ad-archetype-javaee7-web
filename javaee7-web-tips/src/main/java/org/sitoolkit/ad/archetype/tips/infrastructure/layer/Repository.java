package org.sitoolkit.ad.archetype.tips.infrastructure.layer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Stereotype;
import javax.inject.Named;

/**
 * このアノテーションは、リポジトリクラスが宣言すべきステレオタイプです。
 */
@Stereotype
@Named
@ApplicationScoped
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Repository {

}
