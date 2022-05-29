/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 14/03/2018
 */

create table CATALOGO_PRODUCTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CATEGORIA_ID bigint,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT, 
    MODULO_COD varchar(4),
    TIPO_COD varchar(3),
    NOMBRE varchar(100),   
    DESCRIPCION varchar(256),      
    IVA_ID integer,
    ICE_ID integer,
    IRBPNR_ID integer,
    ESTADO varchar(1),
    primary key (ID)
)


