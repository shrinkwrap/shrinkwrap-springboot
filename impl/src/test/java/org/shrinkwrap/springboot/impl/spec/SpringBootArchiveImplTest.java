package org.shrinkwrap.springboot.impl.spec;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.path.BasicPath;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.shrinkwrap.springboot.api.spec.SpringBootArchive;
import org.shrinkwrap.springboot.impl.SpringBootLayouts;
import org.springboot.Application;
import org.springboot.HelloController;

public class SpringBootArchiveImplTest {

    private static String SPRING_BOOT_VERSION_13 = "1.3.7.RELEASE";
    private static String SPRING_BOOT_VERSION_14 = "1.4.4.RELEASE";
    private static String SPRING_BOOT_VERSION_15 = "1.5.1.RELEASE";

    @Test
    public void sb13_should_add_libs_in_lib_directory() {
        final SpringBootArchive archive = prepareSpringBoot13Archive();

        final Node libDirectory = archive.get("/lib");
        assertThat(libDirectory.getChildren())
                .extracting("path")
                .contains(new BasicPath("/lib/spring-boot-starter-web-"+ SPRING_BOOT_VERSION_13 +".jar"));
    }

    @Test
    public void sb14_should_add_libs_in_lib_directory() {
        final SpringBootArchive archive = prepareSpringBoot14Archive();

        final Node libDirectory = archive.get("/BOOT-INF/lib");
        assertThat(libDirectory.getChildren())
                .extracting("path")
                .contains(new BasicPath("/BOOT-INF/lib/spring-boot-starter-web-"+ SPRING_BOOT_VERSION_14 +".jar"));
    }

    @Test
    public void sb13_should_add_manifest_entries() throws IOException {
        final SpringBootArchive archive = prepareSpringBoot13Archive();

        final Asset manifest = archive.get("/META-INF/MANIFEST.MF").getAsset();
        try (final InputStream input = manifest.openStream()) {
            assertThat(read(input))
                    .contains("Main-Class: org.springframework.boot.loader.JarLauncher")
                    .contains("Spring-Boot-Version: "+ SPRING_BOOT_VERSION_13)
                    .contains("Start-Class: " + Application.class.getName());
        }
    }

    @Test
    public void sb13_should_not_support_webinf_resources() {
        final SpringBootArchive archive = prepareSpringBoot13Archive();
        try {
            archive.addAsWebInfResource(EmptyAsset.INSTANCE, "web.xml");
            fail();
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void sb14_should_not_support_webinf_resources() {
        final SpringBootArchive archive = prepareSpringBoot14Archive();
        try {
            archive.addAsWebInfResource(EmptyAsset.INSTANCE, "web.xml");
            fail();
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void sb13_should_not_support_bootinf_resources() {
        final SpringBootArchive archive = prepareSpringBoot13Archive();
        try {
            archive.addAsBootInfResource(EmptyAsset.INSTANCE, "test");
            fail();
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void sb14_should_support_bootinf_resources() {
        final SpringBootArchive archive = prepareSpringBoot14Archive();
        archive.addAsBootInfResource(EmptyAsset.INSTANCE, "test");
        final Node libDirectory = archive.get("/BOOT-INF");
        assertThat(libDirectory.getChildren())
                .extracting("path")
                .contains(new BasicPath("/BOOT-INF/test"));
    }

    @Test
    public void sb14_should_add_manifest_entries() throws IOException {
        final SpringBootArchive archive = prepareSpringBoot14Archive();

        final Asset manifest = archive.get("/META-INF/MANIFEST.MF").getAsset();
        try (final InputStream input = manifest.openStream()) {
            assertThat(read(input))
                    .contains("Main-Class: org.springframework.boot.loader.JarLauncher")
                    .contains("Spring-Boot-Version: "+ SPRING_BOOT_VERSION_14)
                    .contains("Start-Class: " + Application.class.getName());
        }
    }

    @Test
    public void sb13_should_add_launcher() {
        final SpringBootArchive archive = prepareSpringBoot13Archive();

        final Node jarLauncher = archive.get("/org/springframework/boot/loader/JarLauncher.class");
        assertThat(jarLauncher).isNotNull();
    }

    private static SpringBootArchive prepareSpringBoot13Archive() {
        return ShrinkWrap.create(SpringBootArchive.class)
                .setSpringBootLayout(SpringBootLayouts.SPRING_BOOT_10)

                .addClass(Application.class)
                .addClass(HelloController.class)

                .addAsLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-starter-web:"+ SPRING_BOOT_VERSION_13)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .addAsLauncherLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:"+ SPRING_BOOT_VERSION_13)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .setSpringBootManifest(Application.class.getName(), SPRING_BOOT_VERSION_13);
    }

    private static SpringBootArchive prepareSpringBoot14Archive() {
        return ShrinkWrap.create(SpringBootArchive.class)
                .setSpringBootLayout(SpringBootLayouts.SPRING_BOOT_14)

                .addClass(Application.class)
                .addClass(HelloController.class)

                .addAsLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-starter-web:"+ SPRING_BOOT_VERSION_14)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .addAsLauncherLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:"+ SPRING_BOOT_VERSION_14)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .setSpringBootManifest(Application.class.getName(), SPRING_BOOT_VERSION_14);
    }

    private static SpringBootArchive prepareSpringBoot15JARArchive() {
        return ShrinkWrap.create(SpringBootArchive.class)
                .setSpringBootLayout(SpringBootLayouts.SPRING_BOOT_15_JAR)

                .addClass(Application.class)
                .addClass(HelloController.class)

                .addAsLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-starter-web:"+ SPRING_BOOT_VERSION_15)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .addAsLauncherLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:"+ SPRING_BOOT_VERSION_15)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .setSpringBootManifest(Application.class.getName(), SPRING_BOOT_VERSION_15);
    }

    private static String read(InputStream input) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }
}
