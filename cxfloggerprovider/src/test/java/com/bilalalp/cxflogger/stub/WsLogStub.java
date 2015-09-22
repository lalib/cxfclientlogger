package com.bilalalp.cxflogger.stub;

import com.bilalalp.cxflogger.constant.CxfLoggerConstant;
import com.bilalalp.cxflogger.entity.WsLog;

import java.util.Date;

public class WsLogStub {

    private WsLogStub() {

    }

    public static WsLog getFirstWsLog() {

        final WsLog wsLog = new WsLog();
        wsLog.setEncoding(CxfLoggerConstant.UTF8_ENCODING);
        wsLog.setRecordDate(new Date());
        wsLog.setRequestBody("Test Request Body");
        wsLog.setResponseBody("Test Response Body");
        wsLog.setSoapAction("Test Soap Action");
        wsLog.setRequestUrl("Test Request URL");
        return wsLog;
    }
}
