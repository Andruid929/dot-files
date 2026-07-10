package io.github.andruid929.dotfiles.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandAction implements Runnable {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

}
