package com.bilalalp.cxflogger.stub;

import com.bilalalp.cxflogger.dto.WsLogDto;

public class WsLogDtoStub {

    private WsLogDtoStub() {

    }

    public static WsLogDto getFirstWslogDto() {

        final WsLogDto wsLogDto = new WsLogDto();
        wsLogDto.setResponse("test");
        wsLogDto.setRequest("test");
        wsLogDto.setMethod("method");
        wsLogDto.setResponseCode("code");
        return wsLogDto;
    }
}
