package com.bilalalp.cxflogger.interceptor;

import com.bilalalp.cxflogger.constant.CxfLoggerConstant;
import com.bilalalp.cxflogger.dto.MessageWrapper;
import com.bilalalp.cxflogger.dto.WsLogDto;
import com.bilalalp.cxflogger.writer.OutputLogWriter;
import com.bilalalp.cxflogger.writer.OutputStreamLoggingCallback;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

@Service(value = "wsLoggingOutInterceptor")
public class WsLoggingOutInterceptor extends AbstractLoggingInterceptor {

    public WsLoggingOutInterceptor() {
        super(Phase.PRE_STREAM);
    }

    @Override
    public void handleMessage(final Message message) {

        final OutputStream os = message.getContent(OutputStream.class);
        final MessageWrapper messageWrapper = new MessageWrapper(message);

        if (os != null) {
            final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
            message.setContent(OutputStream.class, newOut);
            newOut.registerCallback(new OutputStreamLoggingCallback(messageWrapper, os));
        } else {
            final Writer iowriter = message.getContent(Writer.class);
            if (iowriter != null) {
                message.setContent(Writer.class, new OutputLogWriter(messageWrapper, iowriter));
            }
        }
        final Map<String, Object> headerInformations = getHeaderInformations(message);
        final WsLogDto wsLogDto = messageWrapper.getWsLogDto();

        wsLogDto.put(CxfLoggerConstant.HEADER_INFORMATIONS_PARAM, headerInformations);
        wsLogDto.setMethod(message.get(Message.HTTP_REQUEST_METHOD));
        wsLogDto.setRequestUrl(this.getFullRequestUri(message));
    }

    protected String getFullRequestUri(final Message message) {
        return String.valueOf(message.get(org.apache.cxf.message.Message.ENDPOINT_ADDRESS));
    }

    @Override
    public void handleFault(final Message message) {
        final MessageWrapper messageWrapper = new MessageWrapper(message);
        messageWrapper.getWsLogDto().setFault(true);
        this.handleMessage(message);
    }
}
