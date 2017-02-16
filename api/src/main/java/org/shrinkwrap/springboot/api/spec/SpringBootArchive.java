package org.shrinkwrap.springboot.api.spec;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.container.LibraryContainer;
import org.jboss.shrinkwrap.api.container.ResourceContainer;
import org.jboss.shrinkwrap.api.container.ServiceProviderContainer;
import org.jboss.shrinkwrap.api.container.WebContainer;
import org.shrinkwrap.springboot.api.container.SpringBootContainer;

/**
 * Implements Launching executable JARS for Spring Boot apps, following the description of the format at
 * http://docs.spring.io/spring-boot/docs/1.3.7.RELEASE/reference/htmlsingle/#executable-jar
 * and http://docs.spring.io/spring-boot/docs/1.4.4.RELEASE/reference/htmlsingle/#executable-jar
 * and http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#executable-jar
 *
 * The layout will define the structure of the executable. This implies that setting the layout should be the first
 * method called, or the result of the archive will have resources of the same type placed inside different paths.
 *
 * @author <a href="mailto:rivasdiaz@gmail.com">Ramon Rivas</a>
 */
public interface SpringBootArchive extends Archive<SpringBootArchive>, ResourceContainer<SpringBootArchive>,
        LibraryContainer<SpringBootArchive>, WebContainer<SpringBootArchive>, SpringBootContainer<SpringBootArchive>,
        ServiceProviderContainer<SpringBootArchive> {
}
