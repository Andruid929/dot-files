package io.github.andruid929.dotfiles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.github.andruid929.dotfiles.annotations.Blankable;
import io.github.andruid929.dotfiles.errors.BlankValueException;
import io.github.andruid929.dotfiles.util.PathUtil;

public class Dotfile {

    @NotNull
    private String key;

    @NotNull
    private String filename;

    @NotNull
    private String fileContents;

    @NotNull
    @Blankable
    private String filePath;

    @JsonCreator
    public Dotfile(
            @NotNull @JsonProperty("key") String key,
            @NotNull @JsonProperty("fileContents") String fileContents,
            @NotNull @JsonProperty("filePath") String filePath,
            @NotNull @JsonProperty("filename") String filename) {
        checkForBlanks(key, fileContents);

        this.key = key;
        this.fileContents = fileContents;
        this.filePath = PathUtil.shortenPath(filePath);
        this.filename = filename;
    }

    private void checkForBlanks(@NotNull String key, String fileContents) {
        if (key.isBlank()) {
            throw new BlankValueException("Key is required");
        }

        if (fileContents.isBlank()) {
            throw new BlankValueException("File contents are required");
        }
    }

    @NotNull
    public String getKey() {
        return key;
    }

    public void setKey(@NotNull String key) {
        this.key = key;
    }

    @NotNull
    public String getFilename() {
        return filename;
    }

    public void setFilename(@NotNull String filename) {
        this.filename = filename;
    }

    @NotNull
    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(@NotNull String fileContents) {
        this.fileContents = fileContents;
    }

    @NotNull
    @Blankable
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dotfile dotFile = (Dotfile) o;
        return Objects.equals(key, dotFile.key) && Objects.equals(filename, dotFile.filename) && Objects.equals(fileContents, dotFile.fileContents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, filename, fileContents, filePath);
    }

    @Override
    public String toString() {
        return "Dotfile{key=\"".concat(key).concat("\", ")
                .concat("filename=\"").concat(filename).concat("\", ")
                .concat("filepath=\"").concat(filePath).concat("\", ")
                .concat("content=").concat(fileContents).concat("\"}");
    }
}
