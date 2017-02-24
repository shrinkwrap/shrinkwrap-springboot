package org.shrinkwrap.springboot.api.container;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Filter;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.shrinkwrap.springboot.api.SpringBootLayout;

/**
 * Defines the contract for a component capable of storing spring-boot-related resources.
 *
 * The actual path to the BOOT-INF resources within the Archive is up to the implementations/specifications.
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public interface SpringBootContainer<T extends Archive<T>> {

    // -------------------------------------------------------------------------------------||
    // Contracts ---------------------------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    // -------------------------------------------------------------------------------------||
    // Spring Boot Manifest ----------------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    T setSpringBootLayout(SpringBootLayout layout);

    T setSpringBootManifest(String applicationClassName);

    T setSpringBootManifest(String applicationClassName, String springBootVersion);

    // -------------------------------------------------------------------------------------||
    // Spring Boot BOOT-INF resources ------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Adds the resource as a BOOT-INF resource to the container, returning the container itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourceName
     *            resource to add
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             if resourceName is not specified
     */
    T addAsBootInfResource(String resourceName) throws IllegalArgumentException;

    /**
     * Adds the {@link File} as a BOOT-INF resource to the container, returning the container itself. <br/>
     * The {@link File} will be placed into the Container BOOT-INF path under {@link File#getName()}.
     *
     * @param resource
     *            Resource to add
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             if {@link File} resource is null
     */
    T addAsBootInfResource(File resource) throws IllegalArgumentException;

    /**
     * Adds the resource as a BOOT-INF resource to the container, returning the container itself. <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourceName
     *            resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             if resourceName or target is not specified
     */
    T addAsBootInfResource(String resourceName, String target) throws IllegalArgumentException;

    /**
     * Adds the {@link File} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link File} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(File resource, String target) throws IllegalArgumentException;

    /**
     * Adds the {@link URL} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link URL} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(URL resource, String target) throws IllegalArgumentException;

    /**
     * Adds the {@link Asset} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link Asset} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(Asset resource, String target) throws IllegalArgumentException;

    /**
     * Adds the resource as a BOOT-INF resource to the container, returning the container itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourceName
     *            resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(String resourceName, ArchivePath target) throws IllegalArgumentException;

    /**
     * Adds the {@link File} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link File} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(File resource, ArchivePath target) throws IllegalArgumentException;

    /**
     * Adds the {@link URL} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link URL} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(URL resource, ArchivePath target) throws IllegalArgumentException;

    /**
     * Adds the {@link Asset} as a BOOT-INF resource to the container, returning the container itself.
     *
     * @param resource
     *            {@link Asset} resource to add
     * @param target
     *            The target path within the archive in which to add the resource, relative to the {@link Archive}s
     *            BOOT-INF path.
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the resource or target is not specified
     */
    T addAsBootInfResource(Asset resource, ArchivePath target) throws IllegalArgumentException;

    /**
     * Adds the resources inside the package as multiple BOOT-INF resources to the container, returning the container
     * itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourcePackage
     *            The package of the resources
     * @param resourceNames
     *            The names of the resources inside resourcePackage
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If resourcePackage is null, or if no resourceNames are specified or containing null
     */
    T addAsBootInfResources(Package resourcePackage, String... resourceNames) throws IllegalArgumentException;

    /**
     * Adds the resource as a BOOT-INF resource to the container, returning the container itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourcePackage
     *            The package of the resource
     * @param resourceName
     *            The name of the resource inside resourcePackage
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the package or resource name is not specified
     */
    T addAsBootInfResource(Package resourcePackage, String resourceName) throws IllegalArgumentException;

    /**
     * Adds the resource as a BOOT-INF resource to a specific path inside the container, returning the container itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourcePackage
     *            The package of the resource
     * @param resourceName
     *            The name of the resource inside resoucePackage
     * @param target
     *            The target location inside the container
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the package, resource name, or target is not specified
     */
    T addAsBootInfResource(Package resourcePackage, String resourceName, String target) throws IllegalArgumentException;

    /**
     * Adds the resource as a BOOT-INF resource to a specific path inside the container, returning the container itself. <br/>
     * <br/>
     * The {@link ClassLoader} used to obtain the resource is up to the implementation.
     *
     * @param resourcePackage
     *            The package of the resource
     * @param resourceName
     *            The name of the resource inside resoucePackage
     * @param target
     *            The target location inside the container
     * @return This {@link Archive}
     * @throws IllegalArgumentException
     *             If the package, resource name, or target is not specified
     */
    T addAsBootInfResource(Package resourcePackage, String resourceName, ArchivePath target)
            throws IllegalArgumentException;

    // -------------------------------------------------------------------------------------||
    // Spring Boot launcher classes --------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Adds the {@link Class}, and all member (inner) {@link Class}es to the {@link Archive} as part of the launcher
     * class path.
     *
     * @param clazz The class to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class were specified
     */
    T addLauncherClass(Class<?> clazz) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, and all member (inner) {@link Class}es, with the specified fully-qualified name, loaded
     * by the Thread Context {@link ClassLoader}, to the {@link Archive} as part of the launcher class path.
     *
     * @param fullyQualifiedClassName
     *            The name of the {@link Class} to add
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class name was specified
     * @throws IllegalArgumentException
     *             If the {@link Class} could not be loaded
     */
    T addLauncherClass(String fullyQualifiedClassName) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}, and all member (inner) @link{Class}es, with the specified fully-qualified name, loaded by
     * the specified {@link ClassLoader}, to the {@link Archive} as part of the launcher class path.
     *
     * @param fullyQualifiedClassName
     *            The name of the {@link Class} to add
     * @param cl
     *            The {@link ClassLoader} used to load the Class
     * @return This archive
     * @throws IllegalArgumentException
     *             If no class name was specified
     * @throws IllegalArgumentException
     *             If no {@link ClassLoader} was specified
     * @throws IllegalArgumentException
     *             If the {@link Class} could not be loaded by the target {@link ClassLoader}
     */
    T addLauncherClass(String fullyQualifiedClassName, ClassLoader cl) throws IllegalArgumentException;

    /**
     * Adds the {@link Class}es, and all member (inner) {@link Class}es to the {@link Archive} as part of the launcher
     * class path.
     *
     * @param classes
     *            The classes to add to the Archive
     * @return This archive
     * @throws IllegalArgumentException
     *             If no classes were specified
     */
    T addLauncherClasses(Class<?>... classes) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package} to the {@link Archive}  as part of the launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @param pack
     *            The {@link Package} to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no package were specified
     * @see #addLauncherPackages(boolean, Package...)
     */
    T addPackage(Package pack) throws IllegalArgumentException;

    /**
     * Adds all classes in the default {@link Package} to the {@link Archive} as part of the launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @return This virtual archive
     */
    T addDefaultLauncherPackage();

    /**
     * Adds all classes in the specified {@link Package}s to the {@link Archive} as part of the launcher class path.
     *
     * @param recursive
     *            Should the sub packages be added
     * @param packages
     *            All the packages to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     * @see #addLauncherPackages(boolean, Filter, Package...)
     */
    T addLauncherPackages(boolean recursive, Package... packages) throws IllegalArgumentException;

    /**
     * Adds all classes accepted by the filter in the specified {@link Package}s to the {@link Archive} as part of the
     * launcher class path.<br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive
     *            Should the sub packages be added
     * @param filter
     *            filter out specific classes
     * @param packages
     *            All the packages to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     */
    T addLauncherPackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package} to the {@link Archive} as part of the launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @param pack
     *            Package to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no package were specified
     * @see #addLauncherPackages(boolean, Package...)
     */
    T addLauncherPackage(String pack) throws IllegalArgumentException;

    /**
     * Adds all classes in the specified {@link Package}s to the {@link Archive} as part of the launcher class path.
     *
     * @param recursive
     *            Should the sub packages be added
     * @param packages
     *            All the packages to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     * @see #addLauncherPackages(boolean, Filter, Package...)
     */
    T addLauncherPackages(boolean recursive, String... packages) throws IllegalArgumentException;

    /**
     * Adds all classes accepted by the filter in the specified {@link Package}s to the {@link Archive} as part of the
     * launcher class path.<br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive
     *            Should the sub packages be added
     * @param filter
     *            filter out specific classes
     * @param packages
     *            All the packages to add represented by a String ("my/package")
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             If no packages were specified
     */
    T addLauncherPackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}, and all member (inner) {@link Class}es from the {@link Archive} launcher class path.
     *
     * @param clazz The class to be deleted from the Archive
     * @return This archive
     * @throws IllegalArgumentException If no class was specified
     */
    T deleteLauncherClass(Class<?> clazz) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}, and all member (inner) {@link Class}es, with the specified fully-qualified name, loaded by the
     * Thread Context {@link ClassLoader}, from the {@link Archive} launcher class path.
     *
     * @param fullyQualifiedClassName The name of the {@link Class} to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no class name was specified
     * @throws IllegalArgumentException If the {@link Class} could not be loaded
     */
    T deleteLauncherClass(String fullyQualifiedClassName) throws IllegalArgumentException;

    /**
     * Deletes the {@link Class}es, and all member (inner) {@link Class}es from the {@link Archive} launcher class path.
     *
     * @param classes The classes to be removed from the {@link Archive}
     * @return This archive
     * @throws IllegalArgumentException If no classes were specified
     */
    T deleteLauncherClasses(Class<?>... classes) throws IllegalArgumentException;

    /**
     * Deletes all classes in the specified {@link Package} from the {@link Archive} launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @param pack The {@link Package} to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no package was specified
     * @see #deleteLauncherPackages(boolean, Package...)
     */
    T deleteLauncherPackage(Package pack) throws IllegalArgumentException;

    /**
     * Deletes all classes in the specified {@link Package} from the {@link Archive} launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @param pack Package to be delete represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no package was specified
     * @see #deleteLauncherPackages(boolean, Package...)
     */
    T deleteLauncherPackage(String pack) throws IllegalArgumentException;

    /**
     * Deletes all classes in the default {@link Package} from the {@link Archive} launcher class path.<br/>
     * SubPackages are excluded.
     *
     * @return This archive
     */
    T deleteDefaultLauncherPackage();

    /**
     * Deletes all classes in the specified {@link Package}s from the {@link Archive} launcher class path.
     *
     * @param recursive Should the sub packages be deleted?
     * @param packages All the packages to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified
     * @see #deleteLauncherPackages(boolean, Filter, Package...)
     */
    T deleteLauncherPackages(boolean recursive, Package... packages) throws IllegalArgumentException;

    /**
     * Delete all classes in the specified {@link Package}s from the {@link Archive} launcher class path.
     *
     * @param recursive Should the sub packages be deleted?
     * @param packages All the packages to be deleted represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified
     * @see #deleteLauncherPackages(boolean, Filter, Package...)
     */
    T deleteLauncherPackages(boolean recursive, String... packages) throws IllegalArgumentException;

    /**
     * Deletes all classes accepted by the filter in the specified {@link Package}s from the {@link Archive} launcher
     * class path.<br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive Should the sub packages be deleted?
     * @param filter filter out specific classes
     * @param packages All the packages to be deleted
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified or if no filter was specified
     */
    T deleteLauncherPackages(boolean recursive, Filter<ArchivePath> filter, Package... packages) throws IllegalArgumentException;

    /**
     * Delete all classes accepted by the filter in the specified {@link Package}s from the {@link Archive} launcher
     * class path.<br/>
     *
     * The {@link ArchivePath} returned to the filter is the {@link ArchivePath} of the class, not the final location. <br/>
     * package.MyClass = /package/MyClass.class <br/>
     * <b>not:</b> package.MyClass = /WEB-INF/classes/package/MyClass.class <br/>
     *
     * @param recursive Should the sub packages be deleted?
     * @param filter filter out specific classes
     * @param packages All the packages to be deleted represented by a String ("my/package")
     * @return This archive
     * @throws IllegalArgumentException If no packages were specified or if no filter was specified
     */
    T deleteLauncherPackages(boolean recursive, Filter<ArchivePath> filter, String... packages) throws IllegalArgumentException;

    // -------------------------------------------------------------------------------------||
    // Spring Boot launcher libraries ------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Add another {@link Archive} to this {@link Archive} as a launcher library, returning the container itself.
     *
     * @param archive
     *            {@link Archive} resource to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             if {@link Archive} is null
     */
    T addAsLauncherLibrary(Archive<?> archive) throws IllegalArgumentException;

    /**
     * Add multiple {@link Archive}s to this {@link Archive} as launcher libraries, returning the container itself.
     *
     * @param archives
     *            {@link Archive} resources to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             if {@link Archive} resources are null
     * @see #addAsLauncherLibrary(Archive)
     */
    T addAsLauncherLibraries(Archive<?>... archives) throws IllegalArgumentException;

    /**
     * Add multiple {@link Archive}s to this {@link Archive} as launcher libraries, returning the container itself.
     *
     * @param archives
     *            {@link Archive} resources to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             if {@link Collection} of archives is null
     * @see #addAsLauncherLibrary(Archive)
     */
    T addAsLauncherLibraries(Collection<? extends Archive<?>> archives) throws IllegalArgumentException;

    /**
     * Add multiple {@link Archive}s to this {@link Archive} as llauncher libraries, returning the container itself.
     *
     * @param archives
     *            {@link Archive} resources to add
     * @return This virtual archive
     * @throws IllegalArgumentException
     *             if {@link Collection} of archives is null
     * @see #addAsLauncherLibrary(Archive)
     */
    T addAsLauncherLibraries(Archive<?>[]... archives) throws IllegalArgumentException;
}
