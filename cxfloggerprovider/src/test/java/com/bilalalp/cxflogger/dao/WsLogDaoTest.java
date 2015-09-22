package com.bilalalp.cxflogger.dao;

import com.bilalalp.cxflogger.config.CxfLoggerApplicationTestConfig;
import com.bilalalp.cxflogger.entity.WsLog;
import com.bilalalp.cxflogger.stub.WsLogStub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CxfLoggerApplicationTestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@Transactional(propagation = Propagation.REQUIRED)
public class WsLogDaoTest {

    @Autowired
    private WsLogDao wsLogDao;

    @Test
    public void testPersist() {

        final WsLog firstWsLog = WsLogStub.getFirstWsLog();

        wsLogDao.persist(firstWsLog);

        final WsLog actual = wsLogDao.findById(firstWsLog.getId());

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), firstWsLog.getId());
    }
}
