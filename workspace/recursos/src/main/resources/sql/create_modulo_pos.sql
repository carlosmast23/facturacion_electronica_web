/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS_CODESOFT
 * Created: 14/02/2020
 */

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table CAJA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    SUCURSAL_ID BIGINT,
    PUNTO_EMISION_ID BIGINT,
    NOMBRE varchar(256),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
    
    primary key (ID),
    CONSTRAINT id_caja_sucursal_fk FOREIGN KEY (SUCURSAL_ID) REFERENCES SUCURSAL(ID),
    CONSTRAINT id_caja_punto_emision_fk FOREIGN KEY (PUNTO_EMISION_ID) REFERENCES PUNTO_EMISION(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table VENTA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table ARQUEO_CAJA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.9)*/
    CAJA_SESSION_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.9)*/
    USUARIO_ID BIGINT,
    FECHA_HORA timestamp,
    VALOR_TEORICO varchar(256),
    VALOR_FISICO decimal(13,2),
    ESTADO varchar(1),
    
    primary key (ID)
);


/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table CAJA_SESSION(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    
    CAJA_ID BIGINT,
    USUARIO_ID BIGINT,
    VENTA_ID BIGINT,
    ARQUEO_CAJA_ID BIGINT,
    
    ESTADO varchar(1),
    FECHA_HORA_APERTURA timestamp,
    FECHA_HORA_CIERRE timestamp,
    VALOR_APERTURA decimal(13,2),
    VALOR_CIERRE decimal(13,2),
    ESTADO_CIERRE_CAJA varchar(1),

    primary key (ID),
    constraint id_caja_session_caja_fk foreign key(CAJA_ID) references CAJA(ID),
    constraint id_caja_session_arqueo_caja_fk foreign key(ARQUEO_CAJA_ID) references ARQUEO_CAJA(ID),
    constraint id_caja_session_usuario_fk foreign key(USUARIO_ID) references USUARIO(ID),
    constraint id_caja_session_venta_fk foreign key(VENTA_ID) references VENTA(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table INGRESO_CAJA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    
    CAJA_SESSION_ID BIGINT,
    VENTA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.3)*/
    VALOR decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.3)*/
    FACTURA_ID BIGINT,

    primary key (ID),
    constraint id_ingreso_caja_caja_session_fk foreign key(CAJA_SESSION_ID) references CAJA_SESSION(ID),
    constraint id_ingreso_caja_venta_fk foreign key(VENTA_ID) references VENTA(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table CAJA_PERMISO(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),

    CAJA_ID BIGINT,
    USUARIO_ID BIGINT,

    ESTADO varchar(1),
    DESCRIPCION varchar(1024),

    primary key(ID),
    constraint id_caja_permiso_caja_session_fk foreign key(CAJA_ID) references CAJA(ID),
    constraint id_caja_permiso_usuario_session_fk foreign key(USUARIO_ID) references USUARIO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table TURNO(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    
    NOMBRE varchar(256),
    HORA_INICIAL time,
    HORA_FINAL time,

    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.6.4)*/
create table TURNO_ASIGNADO(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    
    CAJA_PERMISO_ID BIGINT,
    TURNO_ID BIGINT,

    ESTADO varchar(1),
    
    primary key(ID),
    constraint id_turno_asignado_caja_permiso_fk foreign key(CAJA_PERMISO_ID) references CAJA_PERMISO(ID),
    constraint id_turno_asignado_turno_fk foreign key(TURNO_ID) references TURNO(ID)
);

