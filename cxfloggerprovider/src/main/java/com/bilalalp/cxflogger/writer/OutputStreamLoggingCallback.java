package com.bilalalp.cxflogger.writer;

import com.bilalalp.cxflogger.dto.MessageWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;

import java.io.OutputStream;

/**
 * Actual OutputStream of CXF Message will be wrapped with CachedOutputStream. This class is an observer of
 * CachedOutputStream. When ChacedOutputStream is closed 'onClose' method will be invoked. After that we extract
 * output of a service request for logging.
 */
public class OutputStreamLoggingCallback implements CachedOutputStreamCallback {

    private final MessageWrapper message;
    private final OutputStream originalOutputStream;

    public OutputStreamLoggingCallback(MessageWrapper message, OutputStream originalOutputStream) {
        this.message = message;
        this.originalOutputStream = originalOutputStream;
    }

    @Override
    public void onClose(CachedOutputStream cachedOutputStream) {
        final String encoding = message.getEncoding();
        final StringBuilder stringBuilder = new StringBuilder();

        this.writePayload(stringBuilder, cachedOutputStream, encoding);
        this.resetCachedStream(cachedOutputStream);

        message.putRequest(stringBuilder);
        message.setContent(OutputStream.class, originalOutputStream);
    }

    @Override
    public void onFlush(CachedOutputStream os) {

        /** This method is not implemented yet.*/
    }

    protected boolean writePayload(StringBuilder builder, CachedOutputStream cos, String encoding) {
        try {
            cos.writeCacheTo(builder, StringUtils.isEmpty(encoding) ? "UTF-8" : encoding);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean resetCachedStream(CachedOutputStream cachedOutputStream) {
        try {
            cachedOutputStream.lockOutputStream();
            cachedOutputStream.resetOut(null, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
