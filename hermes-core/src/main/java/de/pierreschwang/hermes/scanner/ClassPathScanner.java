package de.pierreschwang.hermes.scanner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassPathScanner {

    private static final String CLASS_EXTENSION = ".class";

    public static List<Class<?>> findClasses(String packageName) throws URISyntaxException, IOException {
        final String packagePath = packageName.replace(".", "/");
        URI uri = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(packagePath)).toURI();

        Path path;
        if (uri.getScheme().equals("jar")) {
            try {
                path = FileSystems.getFileSystem(uri).getPath(packagePath);
            } catch (FileSystemNotFoundException e) {
                path = FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath(packagePath);
            }
        } else {
            path = Paths.get(packagePath);
        }

        try (Stream<Path> stream = Files.walk(path)) {
            return stream.filter(Files::isRegularFile).map(entry -> {
                String classPath = entry.toString().replace("/", ".")
                        .replace("\\", ".");
                try {
                    return Class.forName(classPath.substring(classPath.indexOf(packagePath),
                            classPath.length() - CLASS_EXTENSION.length()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

}
