create table PRODUCTO_PROVEEDOR
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRODUCTO_ID BIGINT,
    PROVEEDOR_ID BIGINT,
    ESTADO varchar(1),
    DESCRIPCION varchar(128),
    CON_IVA varchar(1),
    COSTO_ACTUAL decimal(13,3),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.9.6)*/
    CODIGO_PROVEEDOR varchar(70),
    primary key (ID)
);

