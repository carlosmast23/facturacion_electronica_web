/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Estos documentos son internos del sistema y me permite clasificar a que proceso
 * pertenece o con cual modulo se debe relacionar
 * @author Carlos
 * @nota No mover los codigos anteriores porque en la basese de datos ya estan referenciados a las mismas
 */
public enum TipoDocumentoEnum implements ParametroUtilidades.ComparadorInterface<TipoDocumentoEnum>{
    
     
    /**
     * =========================================================================
     *                ** DOCUMENTO GENERALES DEL SISTEMA  **       
     * =========================================================================
     */
    
    /**
     * Tipo de documento que vincula a un producto pero sin afectar el stock
     */
    LIBRE(ModuloCodefacEnum.FACTURACION, 
            "LIB", 
            "Libre", 
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    
    
        /**
     * Tipo de documento general que uso para clasificar alguna compra como por ejemplo en las retenciones
     */
    COMPRA(ModuloCodefacEnum.COMPRA, 
            "COM", 
            "Compra Simple", 
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA}),
    
    /**
    * Tipo de documento general que uso para clasificar alguna venta como por ejemplo en la pantalla de notas de credito
    */
    VENTA(ModuloCodefacEnum.FACTURACION, 
            "VNT", 
            "Venta", 
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.COMPRA}),
    
    /**
     * Tipo de documento que afecta al stock de los productos
     */
    INVENTARIO(ModuloCodefacEnum.FACTURACION,
            "VET",
            "Inventario",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.INVENTARIO}),
    
    /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_INVENTARIO(ModuloCodefacEnum.COMPRA, 
            "COI", 
            "Compra Inventario", 
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            true),
    
        /**
     * Tipo de compra que va a ingresar productos al inventario
     */
    COMPRA_SERVICIOS(ModuloCodefacEnum.COMPRA,
            "COS",
            "Compra Servicios",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            true),
            

    /**
     * Retenciones del iva o del inventario
     */
    RETENCIONES(ModuloCodefacEnum.COMPRA,
            "RET",
            "Retenciones",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    
    
    
    /**
     * =========================================================================
     *                 ** DOCUMENTO DE INVENTARIO  **       
     * =========================================================================
     */
    ENSAMBLE_INGRESO(ModuloCodefacEnum.INVENTARIO,
            "INV",
            "Ingreso Inventario Ensamble",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),    
    
    ENSAMBLE_CONSTRUIR_VENTA(ModuloCodefacEnum.INVENTARIO,
            "ECV",
            "Ingreso construir venta",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),    

    ENSAMBLE_EGRESO(ModuloCodefacEnum.INVENTARIO,
            "INE",
            "Egreso Inventario Ensamble",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    AGREGAR_MERCADERIA_MANUAL(ModuloCodefacEnum.INVENTARIO,
            "AMM",
            "Ingreso Manual",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            true),
    
    ANULAR_MERCADERIA_POSITIVO(ModuloCodefacEnum.INVENTARIO,
            "AMP",
            "Anulado Stock Positivo",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            true),
    
    ANULAR_MERCADERIA_NEGATIVO(ModuloCodefacEnum.INVENTARIO,
            "AMN",
            "Anulado Stock Negativo",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    TRANSFERENCIA_MERCADERIA_DESTINO(ModuloCodefacEnum.INVENTARIO,
            "TMD",
            "Transferencia mercaderia destino",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    STOCK_INICIAL(ModuloCodefacEnum.INVENTARIO,
            "STI",
            "Ingreso Stock inicial",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            true),
    
    STOCK_AJUSTE_MIGRADO(ModuloCodefacEnum.INVENTARIO,
            "ASM",
            "Ajuste Stock Migrado",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    SALDO_ANTERIOR(ModuloCodefacEnum.INVENTARIO,
            "SAA",
            "Saldo Anterior",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    VENTA_INVENTARIO(ModuloCodefacEnum.INVENTARIO,
            "VEI",
            "Venta inventario",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    NOTA_CREDITO_INVENTARIO(ModuloCodefacEnum.INVENTARIO,
            "NCI",
            "Nota Credito Inventario",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    ELIMINADO_FACTURA(ModuloCodefacEnum.INVENTARIO,
            "ELF",
            "Factura eliminada",
            TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO,
            false),
    
    ELIMINADO_COMPRA(ModuloCodefacEnum.INVENTARIO,
            "COE",
            "Compra eliminada",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    QUITAR_MERCADERIA_MANUAL(ModuloCodefacEnum.INVENTARIO,
            "QMM",
            "Eliminar Manual",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    TRANSFERENCIA_MERCADERIA_ORIGEN(ModuloCodefacEnum.INVENTARIO,
            "TMO",
            "Transferencia mercaderia origen",
            TipoDocumentoEnum.AFECTA_INVENTARIO_NEGATIVO,
            false),
    
    
    
    
    /**
     * =========================================================================
     *              ** DOCUMENTOS PARA EL MODULO ACADEMICO  **       
     * =========================================================================
     */
    ACADEMICO(ModuloCodefacEnum.FACTURACION,
            "ACA","Académico",
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.GESTIONA_ACADEMICA}),
    
  
    
    /**
     * =========================================================================
     *              ** DOCUMENTOS PARA EL MODULO ACADEMICO  **       
     * =========================================================================
     */
    PRESUPUESTOS(ModuloCodefacEnum.FACTURACION,
            "PRE",
            "Presupuestos",
            TipoDocumentoEnum.NO_AFECTA_INVETARIO,
            new ModuloCodefacEnum[]{ModuloCodefacEnum.SERVICIOS});
    
    
    
    public static final String AFECTA_INVENTARIO_POSITIVO="+";
    public static final String AFECTA_INVENTARIO_NEGATIVO="-";
    public static final String NO_AFECTA_INVETARIO="";
    
    /**
     * Constructor para crear documentos con inventario
     */
    private TipoDocumentoEnum(ModuloCodefacEnum moduloEnum,String codigo,String nombre,String signoInventario,Boolean afectaCostoInventario) {
        this.moduloEnum = moduloEnum;
        this.codigo=codigo;
        this.nombre=nombre;        
        this.signoInventario=signoInventario;
        this.modulosPermisos=new ModuloCodefacEnum[0];
        this.afectaCostoInventario=afectaCostoInventario;
    }
    
    private TipoDocumentoEnum(ModuloCodefacEnum moduloEnum,String codigo,String nombre,String signoInventario,ModuloCodefacEnum[] modulosPermisos) {
        this.moduloEnum = moduloEnum;
        this.codigo=codigo;
        this.nombre=nombre;        
        this.signoInventario=signoInventario;
        this.modulosPermisos=modulosPermisos;
    }
    
    private String codigo;
    
    private String nombre;
    
    private ModuloCodefacEnum moduloEnum;
        
    /**
     * El signo del inventario para saber si se tiene que restar o sumar
     */
    private String signoInventario;
    
    
    /**
     * Esta referencia me permite especificar que documentos son los que si tengo que tomar en cuenta para
     * el costeo del producto.
     */
    private Boolean afectaCostoInventario;
    
    /**
     * Lista de los modulos en los cuales son permitidos el tipo de documento
     */
    private ModuloCodefacEnum[] modulosPermisos;
    
    

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public ModuloCodefacEnum getModuloEnum() {
        return moduloEnum;
    }

    public ModuloCodefacEnum[] getModulosPermisos() {
        return modulosPermisos;
    }

    public String getSignoInventario() {
        return signoInventario;
    }
    
    public Integer getSignoInventarioNumero()
    {
        return (signoInventario.equals("+"))?1:-1;
    }

    public Boolean getAfectaCostoInventario() {
        return afectaCostoInventario;
    }
    
    
    
    

    /**
     * Obtiene el objeto por el codigo del tipo de documento
     * @param codigo
     * @return 
     */
    public  static TipoDocumentoEnum obtenerTipoDocumentoPorCodigo(String codigo)
    {
        TipoDocumentoEnum[] tipoDocumentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum tipo : tipoDocumentos) {
            if(tipo.getCodigo().equals(codigo))
            {
                return tipo;
            }
        }
        return null;
    }
    
    
    public  static List<TipoDocumentoEnum> obtenerTipoDocumentoPorModulo(ModuloCodefacEnum modulo)
    {
        List<TipoDocumentoEnum> documentosEnum=new ArrayList<TipoDocumentoEnum>();
        TipoDocumentoEnum[] tipoDocumentos=TipoDocumentoEnum.values();
        for (TipoDocumentoEnum tipo : tipoDocumentos) {
            if(tipo.moduloEnum.equals(modulo))
            {
                documentosEnum.add(tipo);
            }
        }
        return documentosEnum;
    }

    
    public  static List<TipoDocumentoEnum> obtenerTipoDocumentoPorModulo(ModuloCodefacEnum modulo,List<ModuloCodefacEnum> modulosActivos)
    {
        
        List<TipoDocumentoEnum> documentosEnumResultado=new ArrayList<TipoDocumentoEnum>();
        
        List<TipoDocumentoEnum> documentosPorModulo=obtenerTipoDocumentoPorModulo(modulo);
        
        ///Si esta activo el modulo de desarrollo activo todos los documentos y los envio
        if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO))
        {
            return documentosPorModulo;
        }
        
        for (ModuloCodefacEnum modulosActivo : modulosActivos) 
        {
            for (TipoDocumentoEnum tipoDocumento : documentosPorModulo) {

                //Buscar documentos que son permitidos para la lista de modulos activos
                for (ModuloCodefacEnum moduloPermitido : tipoDocumento.getModulosPermisos()) {
                    
                    if (moduloPermitido.equals(modulosActivo)) 
                    {
                        documentosEnumResultado.add(tipoDocumento);
                    }
                }
            }

        }
        
        return documentosEnumResultado;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Override
    public TipoDocumentoEnum consultarParametro(String nombreParametro) {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(nombreParametro);
    }
    
}
