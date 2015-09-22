package com.bilalalp.cxflogger.service;

import com.bilalalp.cxflogger.dto.WsLogDto;

import java.io.Serializable;

public interface WsLogService extends Serializable {

    void log(WsLogDto wsLogDto);
}
