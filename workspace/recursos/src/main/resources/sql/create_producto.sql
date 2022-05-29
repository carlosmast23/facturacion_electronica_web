/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 14-nov-2017
 */
create table PRODUCTO
(
    ID_PRODUCTO BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CATALOGO_PRODUCTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT, 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.9.3)*/
    MARCA_PRODUCTO_ID BIGINT, 
    CODIGO_PERSONALIZADO varchar(70),
    CODIGO_EAN varchar(70),
    CODIGO_UPC varchar(70),
    TIPO_PRODUCTO_COD varchar(1),
    NOMBRE varchar(100),
    VALOR_UNITARIO decimal(13,5),
    ESTADO varchar(1),
    UBICACION varchar(100),
    GARANTIA varchar(1),
    CANTIDAD_MINIMA integer,
    PRECIO_DISTRIBUIDOR decimal(13,5),
    PRECIO_TARJETA decimal(13,5),
    STOCK_INICIAL bigint,
    MARCA varchar(50),
    IMAGEN varchar(100),
    CARACTERISTICAS varchar(1024),
    OBSERVACIONES varchar(1024),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    MANEJAR_INVENTARIO  varchar(1),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.1)*/
    GENERAR_CODIGO_BARRAS  varchar(1), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.2)*/
    TRANSPORTAR_EN_GUIA_REMISION  varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.0.9)*/
    OCULTAR_DETALLE_VENTA varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.5)*/
    PRECIO_SIN_SUBSIDIO decimal(13,5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.5.3)*/
    PVP_4 decimal(13,5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.5.3)*/
    PVP_5 decimal(13,5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.5.3)*/
    PVP_6 decimal(13,5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.1.2)*/
    APLICACION_PRODUCTO varchar(1024),
 

    primary key (ID_PRODUCTO)
);


create table PRODUCTO_ENSAMBLE
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    COMPONENTE_ENSAMBLE BIGINT,
    PRODUCTO_ENSAMBLE BIGINT, 
    CANTIDAD decimal(13,5),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.9.3)*/
create table MARCA_PRODUCTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    EMPRESA_ID BIGINT, 
    NOMBRE varchar(100),
    DESCRIPCION varchar(100),
    ESTADO varchar(1),
    FECHA_CREACION TIMESTAMP,
    FECHA_ULTIMA_MODIFICACION TIMESTAMP,
    primary key (ID)
);


/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.1.2)*/
create table SEGMENTO_PRODUCTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),
    DESCRIPCION varchar(100),
    EMPRESA_ID BIGINT,  

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),
 
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.1.2)*/
create table TIPO_PRODUCTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),
    DESCRIPCION varchar(100),
    EMPRESA_ID BIGINT,  

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),
 
    primary key (ID)
);

