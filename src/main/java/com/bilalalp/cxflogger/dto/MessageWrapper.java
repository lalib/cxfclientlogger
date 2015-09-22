package com.bilalalp.cxflogger.dto;

import com.bilalalp.cxflogger.constant.CxfLoggerConstant;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;

public class MessageWrapper {

    private Message message;

    public MessageWrapper(final Message message) {
        this.message = message;
    }

    public WsLogDto getWsLogDto() {
        final Object object = getWsLogFromMessage();
        if (object == null) {
            this.setWsLog(new WsLogDto());
        }
        return (WsLogDto) getWsLogFromMessage();
    }

    public void setWsLog(final WsLogDto wsLogDto) {
        getExchange().put(CxfLoggerConstant.EXHANGE_KEY, wsLogDto);
    }

    public Object getWsLogFromMessage() {
        return getExchange().get(CxfLoggerConstant.EXHANGE_KEY);
    }

    public Exchange getExchange() {
        return message.getExchange();
    }

    public void putRequest(final StringBuilder stringBuilder) {
        final WsLogDto wsLogDto = this.getWsLogDto();
        wsLogDto.setRequest(stringBuilder.toString());
        this.setWsLog(wsLogDto);
    }

    public <T> void setContent(final Class<T> format, final Object content) {
        message.setContent(format, content);
    }

    public String getEncoding() {
        final Object object = message.get(Message.ENCODING);

        if (object == null) {
            return CxfLoggerConstant.UTF8_ENCODING;
        }

        return (String) object;
    }
}
