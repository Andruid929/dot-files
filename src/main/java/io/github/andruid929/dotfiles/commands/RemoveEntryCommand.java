package io.github.andruid929.dotfiles.commands;

import io.github.andruid929.dotfiles.Worker;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "remove", description = "Remove a saved file")
public final class RemoveEntryCommand extends CommandAction {

    @Parameters(index = "0", defaultValue = "", description = "The key of the dotfile to remove")
    String dotfileKey;

    @Override
    public void run() {
        if (dotfileKey.isBlank()) {
            LOGGER.info("It's hard to remove an element whose key I don't know");
            LOGGER.error("Dotfile key is blank, cannot remove dotfile");

            return;
        }

        boolean removed = Worker.removeDotfile(dotfileKey);

        if (removed) {
            LOGGER.info("Successfully removed file with key '{}'", dotfileKey);

        } else {
            LOGGER.info("No saved file found with key '{}'", dotfileKey);
        }
    }

}
