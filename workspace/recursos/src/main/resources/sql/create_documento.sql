/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 2/04/2019
 */

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.7.8.5)*/
create table TIPO_DOCUMENTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOMBRE varchar(256),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
);