package org.shrinkwrap.springboot.impl.spec;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.shrinkwrap.springboot.api.SpringBootLayout;
import org.shrinkwrap.springboot.api.spec.SpringBootArchive;
import org.shrinkwrap.springboot.impl.SpringBootLayouts;
import org.shrinkwrap.springboot.impl.container.SpringBootContainerBase;

/**
 * Implementation of the {@link SpringBootArchive} interface
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public class SpringBootArchiveImpl extends SpringBootContainerBase<SpringBootArchive> implements SpringBootArchive {

    private SpringBootLayout layout = SpringBootLayouts.DEFAULT;

    @Override
    public SpringBootArchive setSpringBootLayout(SpringBootLayout layout) {
        this.layout = layout;
        return this;
    }

    /**
     * Path to the manifests inside of the Archive.
     */
    private static final ArchivePath PATH_MANIFEST = ArchivePaths.create("META-INF");

    /**
     * Path to web archive service providers.
     */
    private static final ArchivePath PATH_SERVICE_PROVIDERS = ArchivePaths.create(PATH_MANIFEST, "services");

    /**
     * Create a new SpringBootArchive with any type storage engine as backing.
     *
     * @param delegate
     *            The storage backing.
     */
    public SpringBootArchiveImpl(final Archive<?> delegate) {
        super(SpringBootArchive.class, delegate);
    }

    @Override
    protected String getSpringBootLauncherClass() {
        return layout.getLauncherClassName();
    }

    @Override
    protected ArchivePath getServiceProvidersPath() {
        return PATH_SERVICE_PROVIDERS;
    }

    @Override
    protected ArchivePath getManifestPath() {
        return PATH_MANIFEST;
    }

    @Override
    protected ArchivePath getBootInfPath() {
        return layout.getBootInfPath();
    }

    @Override
    protected ArchivePath getWebInfPath() {
        return layout.getWebInfPath();
    }

    @Override
    protected ArchivePath getWebPath() {
        return layout.getWebPath();
    }

    @Override
    protected ArchivePath getResourcePath() {
        return layout.getClassesPath();
    }

    @Override
    protected ArchivePath getClassesPath() {
        return layout.getClassesPath();
    }

    @Override
    protected ArchivePath getLibraryPath() {
        return layout.getLibrariesPath();
    }
}
