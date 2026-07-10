package io.github.andruid929.dotfiles.io;

import static io.github.andruid929.dotfiles.defaults.FilePaths.SAVE_FILE;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.andruid929.dotfiles.annotations.Blankable;

public final class PathReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathReader.class);

    private PathReader() {
    }

    public static @NotNull @Blankable String readFile(Path filePath) {
        try {

            byte[] fileBytes = Files.readAllBytes(filePath);

            return new String(fileBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {

            LOGGER.error("Encountered error reading file '{}'", filePath);
            LOGGER.error("Stacktrace:", e);

            return "";
        }
    }

    @NotNull
    public static String readDotfiles() {
        return readFile(SAVE_FILE);
    }

}
