package io.github.andruid929.dotfiles.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Set;

import io.github.andruid929.dotfiles.Dotfile;

class JsonTransformerTest {

    @Test
    void roundTripTest() {
        var dotfile = new Dotfile("test file", "Hello world",
                "name/andrew", "file.sql");

        Set<Dotfile> dotfilesSet = Set.of(dotfile);

        String jsonString = JsonTransformer.serialiseToJsonString(dotfilesSet);

        Set<Dotfile> deserialisedSet = JsonTransformer.deserialiseToSet(jsonString);

        assertEquals(dotfilesSet, deserialisedSet);

        Dotfile dotfileFromDeserialisedSet = deserialisedSet.stream().toList().getFirst();

        assertEquals("name/andrew", dotfileFromDeserialisedSet.getFilePath());
        assertEquals("Hello world", dotfileFromDeserialisedSet.getFileContents());
    }

}
