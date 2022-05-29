/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.mensajes;

//import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
//import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.MENSAJE_ADVERTENCIA;
//import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.MENSAJE_CORRECTO;
//import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.MENSAJE_INCORRECTO;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_ADVERTENCIA;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_CORRECTO;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_INCORRECTO;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.TITULO_ADVERTENCIA;

/**
 *
 * @author Carlos
 */
public abstract class MensajeCodefacSistema {
    
    public static abstract class Impresiones {

        public static final CodefacMsj IMPRESION_SECCION_INCORRECTA = new CodefacMsj(TITULO_ADVERTENCIA,"Por favor cargue un registro para poder imprimir",MENSAJE_ADVERTENCIA);
    }
    
    public static abstract class AccionesFormulario {

        public static final CodefacMsj GUARDADO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La información fue guardada correctamente",MENSAJE_CORRECTO);
        public static final CodefacMsj EDITADO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La información fue editada correctamente",MENSAJE_CORRECTO);
        public static final CodefacMsj ELIMINADO_CORRECTAMENTE = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La información fue eliminada correctamente",MENSAJE_CORRECTO);
        public static final CodefacMsj NO_PERMITE_EDITAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La pantalla no permite modificaciones",MENSAJE_INCORRECTO);
        public static final CodefacMsj NO_PERMITE_ELIMINAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"Solo se puede eliminar en modo edición",MENSAJE_INCORRECTO);
        public static final CodefacMsj ACCION_PERMITIDA_MODULO_EDITAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"Esta funcionalidad solo esta disponible en el modo editar",MENSAJE_INCORRECTO);
        public static final CodefacMsj PROCESO_CORRECTO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"El proceso finalizo correctamente",MENSAJE_CORRECTO);
        public static final CodefacMsj PROCESO_EN_CURSO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"El proceso comenzó a ejecutarse",MENSAJE_CORRECTO);
        public static final CodefacMsj PROCESO_ESPERA = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"Este proceso puede tardar unos minutos ...",MENSAJE_ADVERTENCIA);
    }
    
    public static abstract class Preguntas
    {
        public static final CodefacMsj GUARDADO = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"Esta seguro que desea eliminar el registro? ",MENSAJE_ADVERTENCIA);
        public static final CodefacMsj ELIMINAR_COMPROBANTE_ELECTRONICO = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"El comprobante electronico se encuentra autorizada en el SRI , \nPor favor elimine solo si tambien esta anulado en el SRI\nDesea eliminar el comprobante de todos modos?" ,MENSAJE_INCORRECTO);
        public static final CodefacMsj ELIMINAR_REGISTRO = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"Esta seguro que desea eliminar el registro ?" ,MENSAJE_INCORRECTO);
        public static final CodefacMsj CARGAR_DATOS = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"Esta seguro que desea cargar los registro ?" ,MENSAJE_INCORRECTO);
    }
    
    public static abstract class ErroresComunes
    {
        public static final CodefacMsj METODO_SIN_IMPLEMENTAR = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"Accion no disponible ",MENSAJE_ADVERTENCIA);
        
    }
    
    public static abstract class Consultas
    {
        public static final CodefacMsj NO_EXISTE_DATO_BUSCAR = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"No existen datos que coincidan con la busqueda",MENSAJE_ADVERTENCIA);        
    }
    
    public static abstract class CorreoElectronico {

        public static final CodefacMsj CORREO_ENVIADO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO, "El correo fue enviado correctamente", MENSAJE_CORRECTO);
        
    }
    
    public static abstract class FacturasMensajes
    {
        public static final CodefacMsj ERROR_ELIMINAR_AFECTA_NOTA_CREDITO = new CodefacMsj(CodefacMsj.TITULO_ERROR,"La Factura no se puede eliminar porque hay una nota de credito que le afecta",MENSAJE_INCORRECTO);
    }
    
    public static abstract class ProformasMensajes
    {
        public static final CodefacMsj PROFORMA_ENVIADA_CORREO = new CodefacMsj("La proforma ?numeroProforma fue generada correctamente", "Estimado ?nombreCliente, ?empresa le informa que su Proforma esta generada correctamente y puede descargar como archivo adjunto. Saludos", MENSAJE_CORRECTO);
    }
    
    public static abstract  class ErrorComunicacion
    {
        public static final CodefacMsj ERROR_COMUNICACION_SERVIDOR = new CodefacMsj(CodefacMsj.TITULO_ERROR,"Error de comunicación con el servidor",MENSAJE_INCORRECTO);
    }
    
    public static abstract  class ErrorValidación
    {
        public static final CodefacMsj ERROR_VALIDAR_NUMEROS = new CodefacMsj(CodefacMsj.TITULO_ERROR,"El formato es incorrecto para el ingreso de números",MENSAJE_INCORRECTO);
    }
   
}
