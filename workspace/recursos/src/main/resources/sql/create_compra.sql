/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/11/2017
 */

create table COMPRA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID BIGINT,
    PROVEEDOR_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2)*/
    ORDEN_COMPRA_ID BIGINT,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_FACTURA date,
    FECHA_CREACION date,
    DESCUENTO_IVA decimal(13,2),
    DESCUENTO_IVA_CERO decimal(13,2),
    SUBTOTAL_IVA decimal(13,2),
    SUBTOTAL_IVA_CERO decimal(13,2),
    IVA decimal(13,2),
    IVA_SRI_ID decimal,
    TOTAL decimal(13,2),
    USUARIO_ID decimal,
    ESTADO varchar(1),
    ESTADO_RETENCION varchar(1),
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    TIPO_FACTURACION varchar(1),
    CODIGO_DOCUMENTO varchar(3),
    CODIGO_TIPO_DOCUMENTO varchar(3),
    INVENTARIO_INGRESO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2)*/
    OBSERVACION varchar(512),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.4)*/
    TIPO_IDENTIFICACION_CODIGO_SRI varchar(3),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.4)*/
    AUTORIZACION varchar(60),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.0)*/
    CODIGO_DOCUMENTO_SRI varchar(16),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.0)*/
    CODIGO_SUSTENTO_SRI varchar(16),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.5)*/
    VALOR_ICE decimal(13,2),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    DIRECCION_ESTABLECIMIENTO varchar(1024),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    DIRECCION_MATRIZ varchar(1024),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    FECHA_AUTORIZACION_SRI date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    FECHA_EMISION date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    TIPO_AMBIENTE varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.0)*/
    SUCURSAL_EMPRESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.7)*/
    CONTRIBUYENTE_ESPECIAL varchar(14),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.5)*/
    PUNTO_EMISION_ID BIGINT,
    primary key (ID)
    /*CONSTRAINT id_cliente_compra_fk FOREIGN KEY (PROVEEDOR_ID) REFERENCES CLIENTE(CLIENTE_ID)*/

);

create table COMPRA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    PRODUCTO_PROVEEDOR_ID BIGINT,
    SRI_RETENCION_IVA_ID BIGINT,
    SRI_RETENCION_RENTA_IVA_ID BIGINT,
    COMPRA_ID BIGINT,
    CANTIDAD decimal(13,3),
    PRECIO_UNITARIO decimal(13,3),
    DESCUENTO decimal(13,2),
    VALOR_ICE decimal,
    VALOR_RETENCION_IVA decimal(13,2),
    VALOR_RETENCION_RENTA decimal(13,2),
    IVA decimal(13,2),
    TOTAL decimal(13,2),
    DESCRIPCION varchar(150),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.4)*/
    CODIGO_SUSTENTO_SRI varchar(16),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.0.1)*/
create table COMPRA_ITEM_REEMBOLSO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    FACTURA_ID BIGINT,
    COMPRA_ID BIGINT,
    TIPO_COMPROBANTE varchar(2),    
    TP_ID_PROV varchar(2),   
    ID_PROV varchar(20), 
    ESTABLECIMIENTO varchar(3), 
    PUNTO_EMISION varchar(3), 
    SECUENCIAL varchar(10), 
    FECHA_EMISION date, 
    AUTORIZACION varchar(50), 
    BASE_IMPONIBLE decimal(13,2),
    BASE_IMP_GRAV decimal(13,2),
    BASE_NO_GRAIVA decimal(13,2),
    BASE_IMP_EXE decimal(13,2),
    MONTO_ICE decimal(13,2),
    MONTO_IVA decimal(13,2),
    primary key (ID)
);

create table ORDEN_COMPRA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    PROVEEDOR_ID BIGINT,
    EMPRESA_ID BIGINT,
    ESTADO varchar(1),
    USUARIO_ID BIGINT,
    FECHA_INGRESO date,
    FECHA_CREACION date,
    OBSERVACION varchar(256),
    CODIGO_TIPO_DOCUMENTO varchar(3),
    IVA_SRI_ID decimal,
    IVA decimal(13,2),
    TOTAL decimal(13,2),
    DESCUENTO_IVA decimal(13,2),
    DESCUENTO_IVA_CERO decimal(13,2),
    SUBTOTAL_IVA decimal(13,2),
    SUBTOTAL_IVA_CERO decimal(13,2),

    primary key (ID)
    /*CONSTRAINT id_cliente_orden_compra_fk FOREIGN KEY (PROVEEDOR_ID) REFERENCES CLIENTE(CLIENTE_ID)*/

);

create table ORDEN_COMPRA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    ORDEN_COMPRA_ID BIGINT,
    PRODUCTO_PROVEEDOR_ID BIGINT,
    CANTIDAD integer ,  
    PRECIO_UNITARIO decimal(13,3),  
    DESCUENTO decimal(13,2),
    VALOR_ICE decimal,
    DESCRIPCION varchar(150),
    TOTAL decimal(13,2),
    IVA decimal(13,2),

    primary key (ID)
);























