package com.bilalalp.cxflogger.interceptor;

import com.bilalalp.cxflogger.dto.MessageWrapper;
import com.bilalalp.cxflogger.dto.WsLogDto;
import com.bilalalp.cxflogger.service.WsLogService;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "wsLoggingInInterceptor")
public class WsLoggingInInterceptor extends AbstractLoggingInterceptor {

    @Lazy
    @Autowired
    private WsLogService wsLogService;

    public WsLoggingInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(final Message message) {
        this.handleMessage(message, false);
    }

    @Override
    public void handleFault(final Message message) {
        this.handleMessage(message, true);
    }

    private void handleMessage(final Message message, final boolean fault) {

        final MessageWrapper messageWrapper = new MessageWrapper(message);
        final WsLogDto wsLogDto = messageWrapper.getWsLogDto();
        final Map<String, Object> headerInformations = getHeaderInformations(message);

        wsLogDto.getHeaderInformations().putAll(headerInformations);
        wsLogDto.setFault(fault);
        wsLogDto.setResponse(this.getBody(message, null));
        wsLogDto.setResponseCode(message.get(Message.RESPONSE_CODE));

        wsLogService.log(wsLogDto);
    }
}