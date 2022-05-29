/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo
 * Created: 30/01/2018
 */

create table CATEGORIA_PRODUCTO( 
    CATEGORIAP_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(100),   
    DESCRIPCION varchar(256),  
    IMAGEN_PATH varchar(512),
    primary key (CATEGORIAP_ID)
)


