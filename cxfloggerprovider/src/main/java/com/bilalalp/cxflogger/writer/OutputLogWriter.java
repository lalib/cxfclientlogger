package com.bilalalp.cxflogger.writer;

import com.bilalalp.cxflogger.dto.MessageWrapper;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class OutputLogWriter extends FilterWriter {

    private final MessageWrapper message;
    private final StringWriter stringWriter;

    public OutputLogWriter(MessageWrapper message, Writer out) {
        super(out);
        this.message = message;
        stringWriter = new StringWriter();
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c);
        stringWriter.write(c);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(cbuf, off, len);
        stringWriter.write(cbuf, off, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        super.write(str, off, len);
        stringWriter.write(str, off, len);
    }

    @Override
    public void close() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringWriter.getBuffer());
        message.putRequest(stringBuilder);
        message.setContent(Writer.class, out);
        super.close();
    }
}
