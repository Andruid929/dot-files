package io.github.andruid929.dotfiles;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.andruid929.dotfiles.io.IOUtil;
import io.github.andruid929.dotfiles.io.PathReader;
import io.github.andruid929.dotfiles.io.PathWriter;
import io.github.andruid929.dotfiles.transformer.JsonTransformer;

public final class Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private static Set<Dotfile> DOTFILES_SET;

    private Worker() {
    }

    public static void addDotfile(Dotfile dotfile) {
        DOTFILES_SET.add(dotfile);
    }

    public static boolean removeDotfile(@NotNull String key) {
        return DOTFILES_SET.removeIf(dotfile -> dotfile.getKey().equals(key));
    }

    public static void start() {
        Runtime.getRuntime().addShutdownHook(gracefulShutdownthread());

        IOUtil.validateAppPaths();

        DOTFILES_SET = ConcurrentHashMap.newKeySet();

        deserialise();
    }

    public static void dismiss() {
        serialise();
    }

    public static Set<Dotfile> getLoadedDotfilesSet() {
        return DOTFILES_SET;
    }

    public static void serialise() {
        String json = JsonTransformer.serialiseToJsonString(DOTFILES_SET);

        if (json.isBlank()) {
            LOGGER.info("Unable to save");
            return;
        }

        PathWriter.writeToDotfiles(json);
    }

    public static void deserialise() {
        String json = PathReader.readDotfiles();

        if (json.isBlank()) {
            return;
        }

        Set<Dotfile> savedDotfiles = JsonTransformer.deserialiseToSet(json);

        if (!savedDotfiles.isEmpty()) {
            DOTFILES_SET.addAll(savedDotfiles);

            LOGGER.debug("Number of loaded dotfiles: {}", savedDotfiles.size());
        }

    }

    private static Thread gracefulShutdownthread() {
        return Thread.ofVirtual()
                .name("graceful-shutdown-worker")
                .uncaughtExceptionHandler((Thread _, Throwable e) -> {
                    LOGGER.error("Graceful shutdown not so graceful", e);
                })
                .unstarted(Worker::serialise);
    }
}
