package io.github.andruid929.dotfiles.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class PathUtil {

    private PathUtil() {
    }

    @Contract("_ -> new")
    public static String @NotNull [] getPathAndFile(@NotNull Path path) {
        if (path.equals(Path.of(""))) {
            return ""
        }

        String filename = path.getFileName().toString();

        String pathToFile;

        if (path.getParent() == null) {
            pathToFile = "";

        } else {
            pathToFile = path.getParent().toString();
        }

        return new String[]{pathToFile, filename};
    }
}
