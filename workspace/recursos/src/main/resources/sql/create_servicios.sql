/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 14/05/2018
 */
create table ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    CLIENTE_ID BIGINT,
    CODIGO varchar(15),
    DESCRIPCION varchar(150),
    ESTADO varchar(10),
    FECHA_INGRESO date,
    primary key(ID)
);

create table DETALLE_ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.1)*/
    DEPARTAMENTO_ID BIGINT,
    EMPLEADO_ID BIGINT,
    ORDEN_TRABAJO_ID BIGINT,
    DESCRIPCION varchar(150),
    NOTAS varchar(150),
    FECHA_ENTREGA date,
    ESTADO varchar(10),
    TIPO_ORDEN_TRABAJO varchar(10),
    PRIORIDAD varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.2)*/
    TITULO varchar(60),
    primary key(ID)
);

create table EMPLEADO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    DEPARTAMENTO_ID BIGINT,
    /*@ELIMINAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    PERSONA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    NACIONALIDAD_ID BIGINT,
    CARGO varchar(30),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    CODIGO varchar(100),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    NOMBRES varchar(100),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    APELLIDOS varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    CEDULA varchar(13),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    DIRECCION varchar(150),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    TELEFONO_CONVENCIONAL varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    TELEFONO_CELULAR varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    CORREO_ELECTRONICO varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    GENERO varchar(1),
    primary key(ID)
);

create table DEPARTAMENTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CODIGO varchar(10),
    NOMBRE varchar(100),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    TIPO varchar(1),
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY(START WITH 1),
    EMPRESA_ID BIGINT,
    USUARIO_ID BIGINT,
    CLIENTE_ID BIGINT,
    CATALOGO_PRODUCTO_ID BIGINT,
    CODIGO varchar(10),
    DESCRIPCION varchar(150),
    OBSERVACIONES varchar(150),
    ESTADO varchar(1),
    FECHA_CREACION date,
    FECHA_PRESUPUESTO date,
    FECHA_VALIDEZ date,
    DESCUENTO_COMPRA decimal(13,2),
    DESCUENTO_VENTA decimal(13,2),
    TOTAL_COMPRA decimal(13,2),
    TOTAL_VENTA decimal(13,2),
    
    ORDEN_TRABAJO_DETALLE_ID BIGINT,
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO_DETALLE
(
    ID BIGINT not null generated always as IDENTITY(start with 1),
    PRECIO_COMPRA decimal(13,2),
    DESCUENTO_COMPRA decimal(13,2),
    PRECIO_VENTA decimal(13,2),
    DESCUENTO_VENTA decimal(13,2),
    CANTIDAD decimal(13,2),
    NUMERO_ORDEN_COMPRA INT,
    ESTADO varchar(1),
    PROVEEDOR_ID BIGINT,
    PRODUCTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2)*/
    PRESUPUESTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    PRODUCTO_PROVEEDOR_ID BIGINT,
    primary key(ID)
);



