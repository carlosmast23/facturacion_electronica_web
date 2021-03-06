/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */

create table CLIENTE( 
    CLIENTE_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NACIONALIDAD_ID bigint,  
    SRI_FORMA_PAGO_ID bigint,    
    SRI_IDENTIFICACION_ID bigint, 
    NOMBRES varchar(256),
    APELLIDOS varchar(256),
    TIPO_OPERADOR varchar(1),
    RAZON_SOCIAL varchar(256),
    NOMBRE_LEGAL varchar(256),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    TIPO_IDENTIFICACION varchar(1),
    IDENTIFICACION varchar(15) not null, 
    TIPO_CLIENTE varchar(12),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(9),
    EXTENSION_TELEFONO varchar(4), 
    TELEFONO_CELULAR varchar(10), 
    CORREO_ELECTRONICO varchar(300),
    ACTIVIDAD_COMERCIAL varchar(100),
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(1),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.2)*/
    CONTACTO_CLIENTES varchar(11), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.2)*/
    CONTACTO_CLIENTES_PORCENTAJE decimal(5,2), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    DIAS_CREDITO_CLIENTE int,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.8)*/
    CONTACTO_CLIENTE_NOMBRE varchar(256),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.5)*/
    DIAS_CREDITO_PROVEEDOR int,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.1)*/
    OBSERVACIONES varchar(5012),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.7)*/
    FECHA_CREACION TIMESTAMP,

    primary key (CLIENTE_ID)
    /*UNIQUE(IDENTIFICACION)*/
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.7.8.0)*/
create table PERSONA_ESTABLECIMIENTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    PERSONA_ID BIGINT, 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    ZONA_ID BIGINT, 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    TIPO_ESTABLECIMIENTO_ID BIGINT, 
    CODIGO_SUCURSAL varchar(3),
    NOMBRE_COMERCIAL varchar(256),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(9),
    EXTENSION_TELEFONO varchar(4), 
    TELEFONO_CELULAR varchar(10), 
    CORREO_ELECTRONICO varchar(300),
    TIPO_SUCURSAL varchar(3),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    LONGITUD decimal(12,8), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    LATITUD decimal(12,8),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    CODIGO_PERSONALIZADO varchar(100), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.4.5)*/
    REFERENCIA_DIRECCION varchar(100), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.9.2)*/
    ESTADO varchar(1),

    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.3.7)*/
create table ZONA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    EMPRESA_ID BIGINT,
    CODIGO varchar(100), 
    NOMBRE varchar(200),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1), 

    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.3.7)*/
create table TIPO_ESTABLECIMIENTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    EMPRESA_ID BIGINT,
    NOMBRE varchar(200),
    DESCRIPCION varchar(1024),
    TIPO varchar(1), 
    ESTADO varchar(1), 

    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.3.8)*/
create table RUTA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    EMPRESA_ID BIGINT,
    EMPLEADO_ID BIGINT,
    CODIGO varchar(100), 
    NOMBRE varchar(200),
    DIA_VISITA INT,
    ESTADO varchar(1), 

    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.3.8)*/
create table RUTA_DETALLE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    PERSONA_ESTABLECIMIENTO_ID BIGINT,
    RUTA_ID BIGINT,
    ORDEN INT,
    ESTADO varchar(1), 

    primary key (ID)
);