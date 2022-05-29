/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS_CODESOFT
 * Created: 28/12/2020
 */

ALTER TABLE factura ADD CONSTRAINT sucursal_fk
Foreign Key (sucursal_id) REFERENCES persona_establecimiento (id);

ALTER TABLE factura ADD CONSTRAINT cliente_fk
Foreign Key (cliente_id) REFERENCES cliente (cliente_id);
