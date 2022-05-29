create table BANCO
(
    ID BIGINT not null ,
    EMPRESA_ID BIGINT,
    CODIGO varchar(30),
    CODIGO_MULTICASH varchar(10),
    NOMBRE varchar(120),
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_banco_empresa_fk FOREIGN KEY (EMPRESA_ID) REFERENCES EMPRESA(ID),
    
);

create table CUENTA_BANCO
(
    ID BIGINT not null ,
    BANCO_ID BIGINT,
    NUMERO_CUENTA varchar(32),
    TIPO_CUENTA varchar(1),
    CLASIFICACION_INTERNA varchar(1),
    SECUENCIAL_CHEQUERA int,
    SALDO_TOTAL decimal(13,2),
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_cuenta_banco_banco_fk FOREIGN KEY (BANCO_ID) REFERENCES CUENTA_BANCO(ID),    
);

create table MOVIMIENTO_CUENTA_BANCO
(
    ID BIGINT not null ,
    CUENTA_BANCO_ID BIGINT,
    VALOR decimal(13,2),
    SIGNO_MOVIMIENTO int,
    TIPO_REFERENCIA varchar(1),
    REFERENCIA_ID BIGINT,
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_movimiento_cuenta_banco_fk FOREIGN KEY (CUENTA_BANCO_ID) REFERENCES MOVIMIENTO_CUENTA_BANCO(ID),    
);