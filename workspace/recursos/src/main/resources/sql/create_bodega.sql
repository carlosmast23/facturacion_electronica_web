create table BODEGA( 
    BODEGA_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),  
    DESCRIPCION varchar(256),
    ENCARGADO varchar(100), 
    IMAGEN_PATH varchar(512),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.8)*/
    SUCURSAL_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.8)*/
    TIPO_BODEGA varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.8)*/
    EMPRESA_ID BIGINT,

    primary key (BODEGA_ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.4.4)*/
create table BODEGA_PERMISO_TRANSFERENCIA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    BODEGA_PRINCIPAL_ID BIGINT,
    BODEGA_PERMISO_ID varchar(100), 
    primary key (ID)
);