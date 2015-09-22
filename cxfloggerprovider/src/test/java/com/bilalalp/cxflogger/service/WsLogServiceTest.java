package com.bilalalp.cxflogger.service;

import com.bilalalp.cxflogger.dao.WsLogDao;
import com.bilalalp.cxflogger.dto.WsLogDto;
import com.bilalalp.cxflogger.entity.WsLog;
import com.bilalalp.cxflogger.stub.WsLogDtoStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WsLogServiceTest {

    @Mock
    private WsLogDao wsLogDao;

    @InjectMocks
    private WsLogServiceImpl wsLogService;

    @Test
    public void log() {

        Mockito.doNothing().when(wsLogDao).persist(Mockito.any(WsLog.class));

        final WsLogDto firstWslogDto = WsLogDtoStub.getFirstWslogDto();
        wsLogService.log(firstWslogDto);

        Mockito.verify(wsLogDao).persist(Mockito.any(WsLog.class));
    }
}
