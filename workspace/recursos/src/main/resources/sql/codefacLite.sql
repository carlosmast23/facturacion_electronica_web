/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 7/11/2017
 */

create table 
    CLIENTE( ID_CLIENTE BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE_SOCIAL varchar(256),
    TIPO_IDENTIFICACION varchar(30), 
    IDENTIFICACION varchar(13), 
    TIPO_CLIENTE varchar(12),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(9),
    EXTENSION_TELEFONO varchar(4), 
    TELEFONO_CELULAR varchar(10), 
    CORREO_ELECTRONICO varchar(60),  
    primary key (ID_CLIENTE));

create table
    EMISOR( ID_EMISOR BIGINT not null GENERATED AYWAYS AS IDENTITY (START WITH 1),
    RAZON_SOCIAL varchar(70),
    RUC varchar(15),
    NOM_COMERCIAL varchar(100),
    DIR_ESTABLECIMIENTO varchar(250),
    COD_ESTABLECIMIENTO varchar(50),
    RESOLUSION varchar(25),
    CONTRIBUYENTE_ESPECIAL varchar(255),
    COD_PUNTO_EMISION varchar(25),
    LLEVA_CONTABILIDAD varchar(1),
    LOGO_IMAGEN varchar(255),
    TIPO_EMISION varchar(1),
    TIEMPO_ESPERA varchar,
    CLAVE_INTERNA varchar(25),
    TIPO_AMBIENTE varchar(1),
    DIRECCION_MATRIZ varchar(255),
    TOKEN varchar(30),
    primary key (ID_EMISOR));


