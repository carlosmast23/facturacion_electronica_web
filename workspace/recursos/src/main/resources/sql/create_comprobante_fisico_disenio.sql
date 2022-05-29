/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 22/01/2018
 */

create table COMPROBANTE_FISICO_DISENIO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    NOMBRE varchar(150),
    CODIGO_DOCUMENTO varchar(3),
    ANCHO BIGINT,
    ALTO BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.0.9)*/
    PPP BIGINT,
    primary key (ID)
);

create table BANDA_COMPROBANTE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    COMPROBANTE_FISICO_ID BIGINT,
    NOMBRE varchar(150),
    TITULO varchar(150),
    ORDEN BIGINT,
    ALTO BIGINT,
    primary key (ID),
    CONSTRAINT id_banda_comprobante_fk FOREIGN KEY (COMPROBANTE_FISICO_ID) REFERENCES COMPROBANTE_FISICO_DISENIO(ID)
);



create table COMPONENTE_COMPROBANTE_FISICO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    BANDA_COMPROBANTE_ID BIGINT,
    NOMBRE varchar(150),
    UUID varchar(150),
    X BIGINT,
    Y BIGINT,
    ANCHO BIGINT,
    ALTO BIGINT,
    TAMANIO_LETRA BIGINT,
    NEGRITA varchar(1),
    OCULTO varchar(1),
    primary key (ID),
    CONSTRAINT id_componente_comprobante_fk FOREIGN KEY (BANDA_COMPROBANTE_ID) REFERENCES BANDA_COMPROBANTE(ID)
);

