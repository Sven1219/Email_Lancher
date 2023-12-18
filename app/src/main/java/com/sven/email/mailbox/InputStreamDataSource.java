package com.sven.email.mailbox;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class InputStreamDataSource implements DataSource {
    private final InputStream inputStream;
    private final String name;

    public InputStreamDataSource(InputStream inputStream, String name) {
        this.inputStream = inputStream;
        this.name = name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getContentType() {
        return "application/octet-stream";
    }

    @Override
    public String getName() {
        return name;
    }
}
