package io.github.andruid929.dotfiles.commands;

import java.util.Scanner;

import io.github.andruid929.dotfiles.io.IOUtil;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "reset", description = "Remove all save files and clear logs")
public final class ResetCommand extends CommandAction {

    @Option(names = {"-l", "--log"})
    private boolean excludeLog = false;

    @Option(names = {"-s", "--save"})
    private boolean excludeSave = false;

    @Override
    public void run() {
        if (excludeSave && excludeLog) {
            LOGGER.info("I have nothing to reset, bye");

            return;
        }

        LOGGER.info("I will reset:");

        if (!excludeLog) {
            LOGGER.info("Log file");
        }

        if (!excludeSave) {
            LOGGER.info("Save file");
        }

        System.out.println();

        System.out.println("Are you sure? (y/n)");

        try (Scanner scanner = new Scanner(System.in)) {
            String response = scanner.next().toLowerCase();

            switch (response) {
                case "y", "yes" -> {

                    if (!excludeLog && !excludeSave) {
                        IOUtil.deleteAppFolder();

                        return;
                    }

                    if (!excludeSave) {
                        IOUtil.deleteDotfiles();
                    }

                    if (!excludeLog) {
                        IOUtil.createLogFile();
                    }
                }
                case "n", "no" -> {
                    LOGGER.info("Reset cancelled");
                }
                default -> LOGGER.info("I wanted a yes or no but anyways");
            }
        }

    }

}
