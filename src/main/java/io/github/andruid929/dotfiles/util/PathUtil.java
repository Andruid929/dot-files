package io.github.andruid929.dotfiles.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import io.github.andruid929.dotfiles.annotations.Blankable;
import io.github.andruid929.leutils.wora.PathFinder;

public final class PathUtil {

    private PathUtil() {
    }

    @Contract("_ -> new")
    public static String @NotNull [] getPathAndFile(@NotNull Path path) {
        if (path.equals(Path.of(""))) {
            return new String[]{};
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

    @Blankable
    public static @NotNull String shortenPath(@NotNull String pathString) {
        if (pathString.isBlank()) {
            return pathString;
        }

        Path path = Path.of(pathString).normalize();

        Path userHome = Path.of(PathFinder.USER_HOME).normalize();

        if (path.startsWith(userHome)) {
            Path relative = userHome.relativize(path);

            return "~/" + relative.toString().replace("\\", "/");
        }

        return pathString;
    }

    @Blankable
    public static @NotNull String expandPath(@NotNull String pathString) {
        if (pathString.isBlank()) {
            return pathString;
        }

        String fromUserHome = pathString.substring(2); //Remove the tilde and first slash

        return PathFinder.createPathFromHomeRoot(fromUserHome).normalize().toString();
    }
}
