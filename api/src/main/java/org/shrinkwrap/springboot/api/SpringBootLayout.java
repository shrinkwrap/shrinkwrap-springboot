package org.shrinkwrap.springboot.api;

import org.jboss.shrinkwrap.api.ArchivePath;

/**
 * Defines the structure of the SpringBootLayout archive
 *
 * @see org.shrinkwrap.springboot.impl.SpringBootLayouts for the list of predefined layouts
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public interface SpringBootLayout {

    String getLauncherClassName();

    ArchivePath getBootInfPath();

    ArchivePath getWebInfPath();

    ArchivePath getLibrariesPath();

    ArchivePath getClassesPath();

    ArchivePath getWebPath();
}
