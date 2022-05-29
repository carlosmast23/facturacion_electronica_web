/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/11/2017
 */
create table 
    SRI_IDENTIFICACION( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CODIGO varchar(256),
    TIPO_TRANSACCION varchar(60), 
    TIPO_IDENTIFICACION varchar(60),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    TIPO_IDENTIFICACION_LETRA varchar(1),
    FECHA_INICIO date,
    FECHA_FIN date,
    primary key (ID))