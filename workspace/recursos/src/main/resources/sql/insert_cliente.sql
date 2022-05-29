/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 05-dic-2017
 */
INSERT INTO CLIENTE(
NOMBRES,
APELLIDOS,
RAZON_SOCIAL,
TIPO_OPERADOR, 
NOMBRE_LEGAL, 
SRI_IDENTIFICACION_ID ,
IDENTIFICACION ,
TIPO_IDENTIFICACION, 
TIPO_CLIENTE, 
DIRECCION, 
TELEFONO_CONVENCIONAL, 
EXTENSION_TELEFONO, 
TELEFONO_CELULAR, 
CORREO_ELECTRONICO,
ESTADO) VALUES (
'Consumidor',
'Final',
'Consumidor Final',
'A',
'Consumidor Final',
5,
'9999999999999',
'F',
'CLIENTE',
'Ecuador',
'022222222',
'0',
'0999999999',
'',
'A');

INSERT INTO PERSONA_ESTABLECIMIENTO(
PERSONA_ID,
CODIGO_SUCURSAL,
NOMBRE_COMERCIAL, 
DIRECCION, 
TELEFONO_CONVENCIONAL ,
EXTENSION_TELEFONO ,
TELEFONO_CELULAR, 
CORREO_ELECTRONICO, 
TIPO_SUCURSAL) 
VALUES (1,'1','Consumidor Final','','','','','','m');