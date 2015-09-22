package com.bilalalp.cxfloggerconsumer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = MyEntity.TABLE_NAME)
@Access(AccessType.FIELD)
public class MyEntity {

    public static final String TABLE_NAME = "MYTABLE";

    private static final String SEQUENCE_NAME = TABLE_NAME + "_SEQ";
    private static final String SEQUENCE_GENERATOR_NAME = "CAS_SEQUENCE_GENERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "C_DESC")
    private String desc;
}
