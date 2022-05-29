/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */
create table 
    USUARIO( 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.1)*/
    EMPLEADO_ID BIGINT ,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.1)*/
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1) ,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT,
    NICK varchar(120) ,
    CLAVE varchar(120), 
    ESTADO varchar(1), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.0.1)*/
    PARAMETROS_COMPROBANTES_ELECTRONICOS varchar(1024), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.8)*/
    FILTRAR_FACTURA varchar(1),
    primary key (ID))
