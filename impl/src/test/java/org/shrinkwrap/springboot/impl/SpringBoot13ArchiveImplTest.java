/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.shrinkwrap.springboot.impl;

import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.path.BasicPath;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.BeforeClass;
import org.junit.Test;
import org.shrinkwrap.springboot.api.SpringBoot13Archive;
import org.springboot.Application;
import org.springboot.HelloController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link SpringBoot13ArchiveImpl}
 *
 * @author <a href="asotobu@gmail.com">Alex Soto</a>
 */
public class SpringBoot13ArchiveImplTest {

    private static SpringBoot13Archive springBoot13Archive;

    @BeforeClass
    public static void prepareSpringBootFile() {
        springBoot13Archive = ShrinkWrap.create(SpringBoot13Archive.class)
                .addClass(HelloController.class)

                .addLibs(Maven.resolver()
                            .resolve("org.springframework.boot:spring-boot-starter-web:1.3.5.RELEASE")
                            .withTransitivity()
                            .as(JavaArchive.class)
                )

                .addLauncher(Maven.resolver()
                            .resolve("org.springframework.boot:spring-boot-loader:1.3.5.RELEASE")
                            .withTransitivity()
                            .as(JavaArchive.class))

                .addSpringBootApplication(Application.class);
    }

    @Test
    public void should_add_libs_in_lib_directory() {
        final Node libDirectory = springBoot13Archive.get("/lib");
        assertThat(libDirectory.getChildren())
                .extracting("path")
                .contains(new BasicPath("/lib/spring-boot-starter-web-1.3.5.RELEASE.jar"));
    }

    @Test
    public void should_add_manifest_entries() {
        final Asset manifest = springBoot13Archive.get("/META-INF/MANIFEST.MF").getAsset();
        final InputStream inputStream = manifest.openStream();
        assertThat(asString(inputStream))
                .isEqualTo("Main-Class: org.springframework.boot.loader.JarLauncher\n" +
                           "Start-Class: " + Application.class.getName() + "\n");
    }

    @Test
    public void should_add_launcher() {
        final Node jarLauncher = springBoot13Archive.get("/org/springframework/boot/loader/JarLauncher.class");
        assertThat(jarLauncher).isNotNull();
    }

    private String asString(InputStream inputStream) {
        String text = null;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        return text;
    }

}
