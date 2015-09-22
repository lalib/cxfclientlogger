package com.bilalalp.cxflogger.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = WsLog.TABLE_NAME)
@Access(AccessType.FIELD)
public class WsLog implements Serializable {

    public static final String TABLE_NAME = "WSLOG";

    private static final String SEQUENCE_NAME = TABLE_NAME + "_SEQ";
    private static final String SEQUENCE_GENERATOR_NAME = "CAS_SEQUENCE_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Version
    @Column(name = "C_VERSION")
    private Long version;

    @Lob
    @Column(name = "C_REQUEST_BODY")
    private String requestBody;

    @Lob
    @Column(name = "C_RESPONSE_BODY")
    private String responseBody;

    @Column(name = "C_REQUEST_URL")
    private String requestUrl;

    @Column(name = "C_RESPONSE_CODE")
    private String responseCode;

    @Column(name = "C_ENCODING")
    private String encoding;

    @Column(name = "C_SOAP_ACTION")
    private String soapAction;

    @Column(name = "C_FAULT")
    private Boolean fault = Boolean.FALSE;

    @Column(name = "C_RECORD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordDate;
}