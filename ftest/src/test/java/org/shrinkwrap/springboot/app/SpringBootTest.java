package org.shrinkwrap.springboot.app;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;

import static io.restassured.RestAssured.get;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.awaitility.Duration;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ZipStoredExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.shrinkwrap.springboot.api.SpringBootLayout;
import org.shrinkwrap.springboot.api.spec.SpringBootArchive;
import org.shrinkwrap.springboot.impl.SpringBootLayoutImpl;
import org.shrinkwrap.springboot.impl.SpringBootLayouts;

/**
 *
 * Functional test that checks that the Spring Boot file created is bootable.
 *
 * @author <a href="rivasdiaz@gmail.com">Ramon Rivas</a>
 *
 */
public class SpringBootTest {

    private static String SPRING_BOOT_VERSION_13 = "1.3.7.RELEASE";
    private static String SPRING_BOOT_VERSION_14 = "1.4.4.RELEASE";
    private static String SPRING_BOOT_VERSION_15 = "1.5.1.RELEASE";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void should_create_valid_spring_boot_13_archive() throws IOException {

        SpringBootArchive archive = createSpringBootArchive(SpringBootLayouts.SPRING_BOOT_10, SPRING_BOOT_VERSION_13);
        validateSpringBootArchive(archive);
    }

    @Test
    public void should_create_valid_spring_boot_14_archive() throws IOException {

        SpringBootArchive archive = createSpringBootArchive(SpringBootLayouts.SPRING_BOOT_14, SPRING_BOOT_VERSION_14);
        validateSpringBootArchive(archive);
    }

    @Test
    public void should_create_valid_spring_boot_15jar_archive() throws IOException {

        SpringBootArchive archive = createSpringBootArchive(SpringBootLayouts.SPRING_BOOT_15_JAR, SPRING_BOOT_VERSION_15);
        validateSpringBootArchive(archive);
    }

    @Test
    public void should_create_valid_spring_boot_15war_archive() throws IOException {

        SpringBootArchive archive = createSpringBootArchive(SpringBootLayouts.SPRING_BOOT_15_WAR, SPRING_BOOT_VERSION_15);
        validateSpringBootArchive(archive);
    }

    @Test
    public void should_create_custom_spring_boot_15jar_archive() throws IOException {

        SpringBootLayout customLayout = new SpringBootLayoutImpl(
                ManifestJarLauncher.class.getName(),
                "/INTERNAL/SPRING-BOOT-INF", "/INTERNAL/WEB-INF", "/INTERNAL/LIBRARIES", "/INTERNAL/CLASSES"
        );
        SpringBootArchive archive = createSpringBootArchive(customLayout, SPRING_BOOT_VERSION_15);
        archive.addLauncherClass(ManifestJarLauncher.class);
        validateSpringBootArchive(archive);
    }

    private void validateSpringBootArchive(SpringBootArchive archive) throws IOException {
        archive.as(ZipStoredExporter.class).exportTo(new File(temporaryFolder.getRoot(), "app.jar"));
        Process process = new ProcessBuilder("java", "-jar", temporaryFolder.getRoot().getAbsolutePath() + "/app.jar").start();
        try {

            final Callable<Integer> statusCode = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    try {
                        URL url = new URL("http://localhost:8080");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        return connection.getResponseCode();
                    } catch(ConnectException e) {
                        return 404;
                    }
                }
            };

            await()
                    .atMost(Duration.TEN_SECONDS)
                    .until(statusCode, equalTo(200));

            // check controller works
            get().then().body(equalTo("Greetings From Spring Boot"));

            // check web resource works
            get("/hello").then().body(equalTo("world"));
        } finally {
            process.destroy();
        }
    }

    private SpringBootArchive createSpringBootArchive(SpringBootLayout layout, String springBootVersion) {
        return ShrinkWrap.create(SpringBootArchive.class)
                .setSpringBootLayout(layout)

                .addClass(Application.class)
                .addClass(HelloController.class)

                .addAsLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-starter-web:"+springBootVersion)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .addAsLauncherLibraries(Maven.resolver()
                        .resolve("org.springframework.boot:spring-boot-loader:"+springBootVersion)
                        .withTransitivity()
                        .as(JavaArchive.class))

                .addAsWebResource(new StringAsset("world"), "hello")

                .setSpringBootManifest(Application.class.getName(), springBootVersion);
    }
}
