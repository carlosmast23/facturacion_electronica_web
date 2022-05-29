/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 5/01/2018
 */
create table ACCESO_DIRECTO
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(200),
    X integer, 
    Y integer, 
    primary key (ID)
)

