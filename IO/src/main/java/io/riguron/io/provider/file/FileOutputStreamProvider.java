package io.riguron.io.provider.file;

import io.riguron.io.provider.OutputStreamProvider;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@AllArgsConstructor
public class FileOutputStreamProvider implements OutputStreamProvider {

    private Path path;

    @Override
    public OutputStream getOutputStream() {
        try {
            Files.createDirectories(path.getParent());
            return Files.newOutputStream(path, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
