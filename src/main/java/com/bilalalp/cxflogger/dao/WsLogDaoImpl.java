package com.bilalalp.cxflogger.dao;

import com.bilalalp.cxflogger.entity.WsLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Getter
@Setter
@Repository
@EnableTransactionManagement
public class WsLogDaoImpl implements WsLogDao {

    @PersistenceContext(unitName = "cxfLoggerEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    @Transactional(value = "cxfLoggerJpaTransactionManager", propagation = Propagation.MANDATORY)
    public void persist(final WsLog wsLog) {
        getEntityManager().persist(wsLog);
    }

    @Override
    @Transactional(value = "cxfLoggerJpaTransactionManager",propagation = Propagation.SUPPORTS)
    public WsLog findById(final Long id) {
        return getEntityManager().find(WsLog.class,id);
    }
}