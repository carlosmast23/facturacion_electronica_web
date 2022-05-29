/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 2/01/2018
 */
create table PERFIL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    /*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT,
    NOMBRE VARCHAR(70),
    DESCRIPCION VARCHAR(150),
    ESTADO varchar(1),
    primary key (ID)
);

create table PERFIL_USUARIO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NICK  varchar(120),
    USUARIO_ID BIGINT,
    PERFIL_ID BIGINT,
    FECHA_CREACION date,
    primary key (ID),
    CONSTRAINT id_usuario_perfil_fk FOREIGN KEY (USUARIO_ID) REFERENCES USUARIO(ID),
    CONSTRAINT id_perfil_usuario_fk FOREIGN KEY (PERFIL_ID) REFERENCES PERFIL(ID)    
);


create table PERMISO_VENTANA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PERFIL_ID  BIGINT,
    NOMBRE_CLASE  varchar(250),
    PERMISO_GRABAR varchar(1),
    PERMISO_ELIMINAR varchar(1),
    PERMISO_IMPRIMIR varchar(1),
    PERMISO_EDITAR varchar(1),
    PERMISO_BUSCAR varchar(1),
    primary key (ID),
    CONSTRAINT id_perfil_fk FOREIGN KEY (PERFIL_ID) REFERENCES PERFIL(ID)
)



