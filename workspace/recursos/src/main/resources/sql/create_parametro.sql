/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 16/11/2017
 */
create table 
    PARAMETRO( ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(200),
    VALOR varchar(2048), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT, 
    primary key (ID));

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.7.0.5)*/
create table
    ACTUALIZAR_SISTEMA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE_METODO varchar(256),
    VERSION varchar(32), 
    CAMBIO_ACTUALIZADO varchar(1),
    DESCRIPCION varchar(1024), 
    primary key (ID));
