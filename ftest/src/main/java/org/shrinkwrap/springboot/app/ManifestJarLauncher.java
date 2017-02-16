package org.shrinkwrap.springboot.app;

import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.springframework.boot.loader.ExecutableArchiveLauncher;
import org.springframework.boot.loader.archive.Archive;

public class ManifestJarLauncher extends ExecutableArchiveLauncher {

    static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";

    static final String BOOT_INF_LIB = "BOOT-INF/lib/";

    private String libsPath;
    private String classesPath;

    public ManifestJarLauncher() {
    }

    protected ManifestJarLauncher(Archive archive) {
        super(archive);
    }

    @Override
    protected List<Archive> getClassPathArchives() throws Exception {
        initializePaths();
        return super.getClassPathArchives();
    }

    protected void initializePaths() throws Exception {
        Manifest manifest = this.getArchive().getManifest();
        if (manifest != null) {
            Attributes attrs = manifest.getMainAttributes();
            libsPath = normalizeToJarPath(attrs.getValue("Spring-Boot-Lib"));
            classesPath = normalizeToJarPath(attrs.getValue("Spring-Boot-Classes"));
        }
        if (libsPath == null) {
            libsPath = BOOT_INF_LIB;
        }
        if (classesPath == null) {
            classesPath = BOOT_INF_CLASSES;
        }
    }

    @Override
    protected boolean isNestedArchive(Archive.Entry entry) {
        if (entry.isDirectory()) {
            return entry.getName().equals(classesPath);
        }
        return entry.getName().startsWith(libsPath);
    }

    public static void main(String[] args) throws Exception {
        new ManifestJarLauncher().launch(args);
    }

    private static String normalizeToJarPath(String path) {
        if (path != null) {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (!path.endsWith("/")) {
                path = path + "/";
            }
        }
        return path;
    }
}
