package org.shrinkwrap.springboot.impl;

import org.shrinkwrap.springboot.api.SpringBootLayout;

/**
 * List of {@link SpringBootLayout} constants for different versions of Spring Boot
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public class SpringBootLayouts {

    public static final SpringBootLayout SPRING_BOOT_10 = new SpringBootLayoutImpl(
            SpringBootLayoutImpl.JAR_LAUNCHER_CLASSNAME, null, null, "/lib", "/"
    );

    public static final SpringBootLayout SPRING_BOOT_14 = new SpringBootLayoutImpl(
            SpringBootLayoutImpl.JAR_LAUNCHER_CLASSNAME, "/BOOT-INF", null, "/BOOT-INF/lib", "/BOOT-INF/classes"
    );

    public static final SpringBootLayout SPRING_BOOT_15_JAR = SPRING_BOOT_14;

    public static final SpringBootLayout SPRING_BOOT_15_WAR = new SpringBootLayoutImpl(
            SpringBootLayoutImpl.WAR_LAUNCHER_CLASSNAME, null, "/WEB-INF", "/WEB-INF/lib", "/WEB-INF/classes"
    );

    public static final SpringBootLayout DEFAULT = SPRING_BOOT_10;
}
