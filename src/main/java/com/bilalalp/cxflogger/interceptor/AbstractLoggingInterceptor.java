package com.bilalalp.cxflogger.interceptor;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedWriter;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLoggingInterceptor extends AbstractInDatabindingInterceptor {

    public AbstractLoggingInterceptor(String phase) {
        super(phase);
    }

    protected String getBody(final Message message, final String encoding) {
        final InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            return getBodyFromInputStream(message, is, encoding);
        } else {
            final Reader reader = message.getContent(Reader.class);
            if (reader != null) {
                return getBodyFromReader(message, reader);
            }
        }

        return null;
    }

    public String getBodyFromInputStream(final Message message, final InputStream is, final String encoding) {
        final StringBuilder builder = new StringBuilder();
        final CachedOutputStream bos = new CachedOutputStream();
        try {
            final InputStream bis = is instanceof DelegatingInputStream ? ((DelegatingInputStream) is).getInputStream() : is;

            IOUtils.copy(bis, bos);
            bos.flush();
            final InputStream newBis = new SequenceInputStream(bos.getInputStream(), bis);

            if (is instanceof DelegatingInputStream) {
                ((DelegatingInputStream) is).setInputStream(newBis);
            } else {
                message.setContent(InputStream.class, newBis);
            }

            writePayload(builder, bos, encoding);

            bos.close();
        } catch (Exception e) {
            throw new Fault(e);
        }

        return builder.toString();
    }

    public String getBodyFromReader(Message message, Reader reader) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            final CachedWriter writer = new CachedWriter();
            IOUtils.copyAndCloseInput(reader, writer);
            message.setContent(Reader.class, writer.getReader());
            writer.writeCacheTo(stringBuilder);
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    public void writePayload(final StringBuilder builder, final CachedOutputStream cos, final String encoding) throws IOException {
        if (StringUtils.isEmpty(encoding)) {
            cos.writeCacheTo(builder);
        } else {
            cos.writeCacheTo(builder, encoding);
        }
    }

    public Map<String, Object> getHeaderInformations(final Message message) {
        final Map<String, Object> objectMap = new HashMap<>();

        final Object protocolHeaders = message.get(Message.PROTOCOL_HEADERS);
        if (protocolHeaders != null) {
            final Map<String, Object> protocolHeaderMap = (Map<String, Object>) protocolHeaders;
            for (final Map.Entry<String, Object> entry : protocolHeaderMap.entrySet()) {
                objectMap.put(entry.getKey(), entry.getValue());
            }
        }

        return objectMap;
    }
}
