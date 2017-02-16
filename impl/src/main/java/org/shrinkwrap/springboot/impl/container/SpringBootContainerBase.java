package org.shrinkwrap.springboot.impl.container;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Filter;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.asset.UrlAsset;
import org.jboss.shrinkwrap.api.exporter.ZipStoredExporter;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.se.manifest.ManifestDescriptor;
import org.jboss.shrinkwrap.impl.base.Validate;
import org.jboss.shrinkwrap.impl.base.asset.AssetUtil;
import org.jboss.shrinkwrap.impl.base.container.WebContainerBase;
import org.jboss.shrinkwrap.impl.base.path.BasicPath;
import org.jboss.shrinkwrap.impl.base.spec.JavaArchiveImpl;
import org.shrinkwrap.springboot.api.container.SpringBootContainer;
import org.shrinkwrap.springboot.impl.SpringBootManifestAttributes;

/**
 * Abstract class that helps implement the {@link SpringBootContainer}. Used by specs that extends the SpringBootContainer.
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public abstract class SpringBootContainerBase<T extends Archive<T>> extends WebContainerBase<T> implements SpringBootContainer<T> {

    private static String NO_VERSION_SPECIFIED = ".";

    private JavaArchiveImpl launcherArchiveView;

    protected SpringBootContainerBase(Class<T> actualType, Archive<?> archive) {
        super(actualType, archive);
        launcherArchiveView = new JavaArchiveImpl(archive);
    }

    /**
     * Returns the Spring Boot launcher class name
     *
     * @return the Spring Boot launcher class name
     */
    protected abstract String getSpringBootLauncherClass();

    /**
     * Returns the path to BOOT-INF
     *
     * @return the path to BOOT-INF
     */
    protected abstract ArchivePath getBootInfPath();

    @Override
    public T setSpringBootManifest(String applicationClassName) {
        Validate.notNullOrEmpty(applicationClassName, "ApplicationClassName must be specified");
        return setSpringBootManifest(applicationClassName, NO_VERSION_SPECIFIED);
    }

    @Override
    public T setSpringBootManifest(String applicationClassName, String springBootVersion) {
        Validate.notNullOrEmpty(applicationClassName, "ApplicationClassName must be specified");
        Validate.notNullOrEmpty(springBootVersion, "springBootVersion must be specified and can not contain null values");

        ManifestDescriptor manifest = Descriptors.create(ManifestDescriptor.class)
                .mainClass(getSpringBootLauncherClass())
                .attribute(SpringBootManifestAttributes.ATTR_START_CLASS, applicationClassName)
                .attribute(SpringBootManifestAttributes.ATTR_SPRING_BOOT_LIB, getLibraryPath().get())
                .attribute(SpringBootManifestAttributes.ATTR_SPRING_BOOT_CLASSES, getClassesPath().get());
        if (!NO_VERSION_SPECIFIED.equals(springBootVersion)) {
            manifest.attribute(SpringBootManifestAttributes.ATTR_SPRING_BOOT_VERSION, springBootVersion);
        }
        return setManifest(new StringAsset(manifest.exportAsString()));
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(String)
     */
    @Override
    public T addAsBootInfResource(final String resourceName) throws IllegalArgumentException {
        Validate.notNull(resourceName, "ResourceName should be specified");

        return addAsBootInfResource(new ClassLoaderAsset(resourceName),
                AssetUtil.getNameForClassloaderResource(resourceName));
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(File)
     */
    @Override
    public T addAsBootInfResource(final File resource) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");

        return addAsBootInfResource(new FileAsset(resource), resource.getName());
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(String, String)
     */
    @Override
    public T addAsBootInfResource(final String resourceName, final String target) throws IllegalArgumentException {
        Validate.notNull(resourceName, "ResourceName should be specified");
        Validate.notNull(target, "Target should be specified");

        return addAsBootInfResource(new ClassLoaderAsset(resourceName), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(File, String)
     */
    @Override
    public T addAsBootInfResource(final File resource, final String target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNullOrEmpty(target, "Target should be specified");

        return addAsBootInfResource(new FileAsset(resource), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(URL, String)
     */
    @Override
    public T addAsBootInfResource(final URL resource, final String target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNullOrEmpty(target, "Target should be specified");

        return addAsBootInfResource(new UrlAsset(resource), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(Asset,
     *      String)
     */
    @Override
    public T addAsBootInfResource(final Asset resource, final String target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNullOrEmpty(target, "Target should be specified");

        return addAsBootInfResource(resource, ArchivePaths.create(target));
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(String,
     *      ArchivePath)
     */
    @Override
    public T addAsBootInfResource(final String resourceName, final ArchivePath target) throws IllegalArgumentException {
        Validate.notNull(resourceName, "ResourceName should be specified");
        Validate.notNull(target, "Target should be specified");

        return addAsBootInfResource(new ClassLoaderAsset(resourceName), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(File,
     *      ArchivePath)
     */
    @Override
    public T addAsBootInfResource(final File resource, final ArchivePath target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNull(target, "Target should be specified");

        return addAsBootInfResource(new FileAsset(resource), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(URL,
     *      ArchivePath)
     */
    @Override
    public T addAsBootInfResource(final URL resource, final ArchivePath target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNull(target, "Target should be specified");

        return addAsBootInfResource(new UrlAsset(resource), target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(Asset,
     *      ArchivePath)
     */
    @Override
    public T addAsBootInfResource(final Asset resource, final ArchivePath target) throws IllegalArgumentException {
        Validate.notNull(resource, "Resource should be specified");
        Validate.notNull(target, "Target should be specified");

        final ArchivePath location = new BasicPath(getBootInfPath(), target);
        return add(resource, location);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResources(Package, String[])
     */
    @Override
    public T addAsBootInfResources(final Package resourcePackage, final String... resourceNames)
            throws IllegalArgumentException {
        Validate.notNull(resourcePackage, "ResourcePackage must be specified");
        Validate.notNullAndNoNullValues(resourceNames,
                "ResourceNames must be specified and can not container null values");
        for (String resourceName : resourceNames) {
            addAsBootInfResource(resourcePackage, resourceName);
        }
        return covarientReturn();
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(Package, String)
     */
    @Override
    public T addAsBootInfResource(final Package resourcePackage, final String resourceName)
            throws IllegalArgumentException {
        Validate.notNull(resourcePackage, "ResourcePackage must be specified");
        Validate.notNull(resourceName, "ResourceName must be specified");

        final String classloaderResourceName = AssetUtil.getClassLoaderResourceName(resourcePackage, resourceName);
        final ArchivePath target = ArchivePaths.create(classloaderResourceName);

        return addAsBootInfResource(resourcePackage, resourceName, target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(Package, String,
     *      String)
     */
    @Override
    public T addAsBootInfResource(final Package resourcePackage, final String resourceName, final String target)
            throws IllegalArgumentException {
        Validate.notNull(resourcePackage, "ResourcePackage must be specified");
        Validate.notNullOrEmpty(resourceName, "ResourceName must be specified");
        Validate.notNullOrEmpty(target, "Target must be specified");

        return addAsBootInfResource(resourcePackage, resourceName, ArchivePaths.create(target));
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsBootInfResource(Package, String,
     *      ArchivePath)
     */
    @Override
    public T addAsBootInfResource(final Package resourcePackage, final String resourceName, ArchivePath target)
            throws IllegalArgumentException {
        Validate.notNull(resourcePackage, "ResourcePackage must be specified");
        Validate.notNullOrEmpty(resourceName, "ResourceName must be specified");
        Validate.notNull(target, "Target must be specified");

        final String classloaderResourceName = AssetUtil.getClassLoaderResourceName(resourcePackage, resourceName);
        final Asset resource = new ClassLoaderAsset(classloaderResourceName);

        return addAsBootInfResource(resource, target);
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsLauncherLibrary(Archive)
     */
    @Override
    public T addAsLauncherLibrary(final Archive<?> archive) throws IllegalArgumentException {
        Validate.notNull(archive, "Archive must be specified");
        return merge(archive, path -> !path.get().startsWith("/META-INF"));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.shrinkwrap.springboot.api.container.SpringBootContainer#addAsLauncherLibraries(org.jboss.shrinkwrap.api.Archive<?>[])
     */
    @Override
    public T addAsLauncherLibraries(Archive<?>... archives) throws IllegalArgumentException {
        Validate.notNull(archives, "Archives must be specified");
        for (final Archive<?> archive : archives)
            addAsLauncherLibrary(archive);
        return covarientReturn();
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsLauncherLibraries(Collection)
     */
    @Override
    public T addAsLauncherLibraries(final Collection<? extends Archive<?>> archives) throws IllegalArgumentException {
        Validate.notNull(archives, "Archives must be specified");
        return addAsLauncherLibraries(archives.toArray(new Archive<?>[archives.size()]));
    }

    /**
     * {@inheritDoc}
     *
     * @see SpringBootContainer#addAsLauncherLibraries(Collection)
     */
    @Override
    public T addAsLauncherLibraries(final Archive<?>[]... archives) throws IllegalArgumentException {
        Validate.notNullAndNoNullValues(archives, "Archives must be specified");
        for (Archive<?>[] archiveArray : archives) {
            for (Archive<?> archive : archiveArray) {
                addAsLauncherLibrary(archive);
            }
        }
        return covarientReturn();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.jboss.shrinkwrap.api.container.LibraryContainer#addAsLibrary(Archive)
     */
    @Override
    public T addAsLibrary(final Archive<?> archive) throws IllegalArgumentException {
        Validate.notNull(archive, "Archive must be specified");
        // Libraries are JARs, so add as ZIP
        return add(archive, getLibraryPath(), ZipStoredExporter.class);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.shrinkwrap.api.container.ManifestContainer#addServiceProvider(java.lang.Class,
     * java.lang.Class<?>[])
     */
    @Override
    public T addAsServiceProvider(Class<?> serviceInterface, Class<?>... serviceImpls) throws IllegalArgumentException {
        launcherArchiveView.addAsServiceProvider(serviceInterface, serviceImpls);
        return covarientReturn();
    }

    /* (non-Javadoc)
    * @see org.jboss.shrinkwrap.impl.base.container.ContainerBase#addAsServiceProvider(java.lang.String, java.lang.String[])
    */
    @Override
    public T addAsServiceProvider(String serviceInterface, String... serviceImpls) throws IllegalArgumentException
    {
        launcherArchiveView.addAsServiceProvider(serviceInterface, serviceImpls);
        return covarientReturn();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.shrinkwrap.api.container.ServiceProviderContainer#addServiceProviderAndClasses(java.lang.Class,
     * java.lang.Class<?>[])
     */
    @Override
    public T addAsServiceProviderAndClasses(Class<?> serviceInterface, Class<?>... serviceImpls)
            throws IllegalArgumentException {
        launcherArchiveView.addAsServiceProvider(serviceInterface, serviceImpls);
        return covarientReturn();
    }

    @Override
    public T addLauncherClass(Class<?> clazz) throws IllegalArgumentException {
        launcherArchiveView.addClass(clazz);
        return covarientReturn();
    }

    @Override
    public T addLauncherClass(String fullyQualifiedClassName) throws IllegalArgumentException {
        launcherArchiveView.addClass(fullyQualifiedClassName);
        return covarientReturn();
    }

    @Override
    public T addLauncherClass(String fullyQualifiedClassName, ClassLoader cl) throws IllegalArgumentException {
        launcherArchiveView.addClass(fullyQualifiedClassName, cl);
        return covarientReturn();
    }

    @Override
    public T addLauncherClasses(Class<?>[] classes) throws IllegalArgumentException {
        launcherArchiveView.addClasses(classes);
        return covarientReturn();
    }

    @Override
    public T addDefaultLauncherPackage() {
        launcherArchiveView.addDefaultPackage();
        return covarientReturn();
    }

    @Override
    public T addLauncherPackages(boolean recursive, Package... packages) throws IllegalArgumentException {
        launcherArchiveView.addPackages(recursive, packages);
        return covarientReturn();
    }

    @Override
    public T addLauncherPackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException {
        launcherArchiveView.addPackages(recursive, filter, packages);
        return covarientReturn();
    }

    @Override
    public T addLauncherPackage(String pack) throws IllegalArgumentException {
        launcherArchiveView.addPackage(pack);
        return covarientReturn();
    }

    @Override
    public T addLauncherPackages(boolean recursive, String... packages) throws IllegalArgumentException {
        return null;
    }

    @Override
    public T addLauncherPackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException {
        launcherArchiveView.addPackages(recursive, filter, packages);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherClass(Class<?> clazz) throws IllegalArgumentException {
        launcherArchiveView.deleteClass(clazz);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherClass(String fullyQualifiedClassName) throws IllegalArgumentException {
        launcherArchiveView.deleteClass(fullyQualifiedClassName);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherClasses(Class<?>[] classes) throws IllegalArgumentException {
        launcherArchiveView.deleteClasses(classes);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackage(Package pack) throws IllegalArgumentException {
        launcherArchiveView.deletePackage(pack);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackage(String pack) throws IllegalArgumentException {
        launcherArchiveView.deletePackage(pack);
        return covarientReturn();
    }

    @Override
    public T deleteDefaultLauncherPackage() {
        launcherArchiveView.deleteDefaultPackage();
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackages(boolean recursive, Package... packages) throws IllegalArgumentException {
        launcherArchiveView.deletePackages(recursive, packages);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackages(boolean recursive, String... packages) throws IllegalArgumentException {
        launcherArchiveView.deletePackages(recursive, packages);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException {
        launcherArchiveView.deletePackages(recursive, filter, packages);
        return covarientReturn();
    }

    @Override
    public T deleteLauncherPackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException {
        launcherArchiveView.deletePackages(recursive, filter, packages);
        return covarientReturn();
    }
}
