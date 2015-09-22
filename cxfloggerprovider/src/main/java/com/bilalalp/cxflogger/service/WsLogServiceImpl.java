package com.bilalalp.cxflogger.service;

import com.bilalalp.cxflogger.dao.WsLogDao;
import com.bilalalp.cxflogger.dto.WsLogDto;
import com.bilalalp.cxflogger.entity.WsLog;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Setter
@Service
public class WsLogServiceImpl implements WsLogService {

    @Autowired
    private WsLogDao wsLogDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = "cxfLoggerJpaTransactionManager")
    public void log(final WsLogDto wsLogDto) {

        final WsLog wsLog = converWsLogDtoToWsLog(wsLogDto);
        wsLogDao.persist(wsLog);
    }

    private WsLog converWsLogDtoToWsLog(final WsLogDto wsLogDto) {

        final WsLog wsLog = new WsLog();
        wsLog.setEncoding(wsLogDto.getEncoding());
        wsLog.setRequestBody(wsLogDto.getRequest());
        wsLog.setResponseBody(wsLogDto.getResponse());
        wsLog.setRequestUrl(wsLogDto.getRequestURL());
        wsLog.setResponseCode(wsLogDto.getResponseCode());
        wsLog.setSoapAction(wsLogDto.getSoapAction());
        wsLog.setFault(wsLogDto.getFault());
        wsLog.setRecordDate(new Date());

        return wsLog;
    }
}