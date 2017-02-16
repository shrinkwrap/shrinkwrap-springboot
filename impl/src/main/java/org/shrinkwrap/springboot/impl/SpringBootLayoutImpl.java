package org.shrinkwrap.springboot.impl;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.shrinkwrap.springboot.api.SpringBootLayout;

/**
 * Implementation of the {@link SpringBootLayout} interface
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public class SpringBootLayoutImpl implements SpringBootLayout {

    public static final String JAR_LAUNCHER_CLASSNAME = "org.springframework.boot.loader.JarLauncher";
    public static final String WAR_LAUNCHER_CLASSNAME = "org.springframework.boot.loader.WarLauncher";

    private final String launcherClassName;

    private final ArchivePath bootInfPath;
    private final ArchivePath webInfPath;
    private final ArchivePath librariesPath;
    private final ArchivePath classesPath;
    private final ArchivePath webPath;

    public SpringBootLayoutImpl(String launcherClassName, String bootInfPath, String webInfPath, String librariesPath, String classesPath) {
        if (launcherClassName == null) {
            throw new IllegalArgumentException("launcherClassName cannot be null");
        }
        if (librariesPath == null) {
            throw new IllegalArgumentException("librariesPath cannot be null");
        }
        if (classesPath == null) {
            throw new IllegalArgumentException("classesPath cannot be null");
        }
        this.launcherClassName = launcherClassName;
        this.bootInfPath = bootInfPath != null ? ArchivePaths.create(bootInfPath) : null;
        this.webInfPath = webInfPath != null ? ArchivePaths.create(webInfPath) : null;
        this.librariesPath = ArchivePaths.create(librariesPath);
        this.classesPath = ArchivePaths.create(classesPath);
        this.webPath = ArchivePaths.create(classesPath, "static");
    }

    @Override
    public String getLauncherClassName() {
        return launcherClassName;
    }

    @Override
    public ArchivePath getBootInfPath() {
        if (bootInfPath == null) throw new UnsupportedOperationException();
        return bootInfPath;
    }

    @Override
    public ArchivePath getWebInfPath() {
        if (webInfPath == null) throw new UnsupportedOperationException();
        return webInfPath;
    }

    @Override
    public ArchivePath getLibrariesPath() {
        return librariesPath;
    }

    @Override
    public ArchivePath getClassesPath() {
        return classesPath;
    }

    @Override
    public ArchivePath getWebPath() {
        return webPath;
    }
}
