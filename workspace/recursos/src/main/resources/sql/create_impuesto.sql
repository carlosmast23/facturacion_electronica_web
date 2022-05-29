/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 14-nov-2017
 */
create table IMPUESTO 
(
    ID_IMPUESTO BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    NOMBRE varchar(10),
    CODIGO_SRI varchar(4),
    DESCRIPCION varchar(60),
    primary key(ID_IMPUESTO)
);

create table SRI_RETENCION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    NOMBRE varchar(60),
    CODIGO_SRI varchar(4),
    primary key(ID)
);


create table SRI_RETENCION_IVA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    RETENCION_ID BIGINT,
    NOMBRE varchar(10),
    CODIGO_SRI varchar(4),
    PORCENTAJE integer,
    DESCRIPCION varchar(60),
    FECHA_INICIO date,
    FECHA_FIN date,
    primary key(ID)
);

create table SRI_RETENCION_RENTA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    RETENCION_ID BIGINT,
    NOMBRE varchar(256),
    CODIGO_SRI varchar(4),
    PORCENTAJE decimal(13,4),
    DESCRIPCION varchar(512),
    FECHA_INICIO date,
    FECHA_FIN date,
    primary key(ID)
);
