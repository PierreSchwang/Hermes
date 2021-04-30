package de.pierreschwang.hermes.annotation;

import de.pierreschwang.hermes.data.PluginLoadState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HermesConfiguration {

    /* PluginYML related stuff */
    String pluginName();

    String version();

    String[] authors();

    PluginLoadState load() default PluginLoadState.POSTWORLD;

    String[] dependencies() default {};

    String[] softDependencies() default {};

    String[] loadBefore() default {};

    String apiVersion() default "";

    /* Hermes AutoInject and registering stuff */

    String serviceSearchPackage() default "";

    String listenerSearchPackage() default "";

}
