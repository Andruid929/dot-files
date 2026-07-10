package io.github.andruid929.dotfiles.commands;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

import io.github.andruid929.dotfiles.Dotfile;
import io.github.andruid929.dotfiles.Worker;
import io.github.andruid929.dotfiles.errors.BlankValueException;
import io.github.andruid929.dotfiles.io.PathReader;
import io.github.andruid929.dotfiles.util.PathUtil;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "save", description = "Save new config")
public class NewEntryCommand extends CommandAction {

    @Option(
            names = {"-k", "--key"},
            defaultValue = "",
            required = true,
            description = "The key for this file")
    String key;

    @Option(
            names = {"-f", "--file"},
            defaultValue = "",
            required = true,
            description = "Where the file is located")
    String filePath;

    @Override
    public void run() {
        try {

            Path fileToRead = getFilePathToRead();

            if (fileToRead == null) {
                return;
            }

            String content = PathReader.readFile(fileToRead);

            if (content.isBlank()) {
                LOGGER.info("I am unable");

                return;
            }

            String[] fileAndPath = PathUtil.getPathAndFile(fileToRead);

            Dotfile dotfile = new Dotfile(key, content, fileAndPath[0], fileAndPath[1]);

            Worker.addDotfile(dotfile);

            LOGGER.info("Successfully added new file with key '{}'", key);

        } catch (BlankValueException e) {
            LOGGER.info("You left something blank: {}", e.getMessage());

            LOGGER.error("Error adding dotfile: ", e);
        }
    }

    @Nullable
    private Path getFilePathToRead() {
        Path pathToFile = Path.of(filePath);

        if (Files.exists(pathToFile)) {
            return pathToFile;
        }

        LOGGER.info("I cannot find the file '{}', you sure it exists?", pathToFile);
        LOGGER.error("Unable to find file '{}'", pathToFile);

        return null;

    }
}
