package com.bilalalp.cxflogger.dao;

import com.bilalalp.cxflogger.entity.WsLog;

public interface WsLogDao {

    void persist(WsLog wsLog);

    WsLog findById(Long id);
}