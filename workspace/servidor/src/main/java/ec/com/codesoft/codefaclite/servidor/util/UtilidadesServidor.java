/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.interfacePanel.ServidorMonitorUpdateInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.EmpresaLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.test.CrearBaseDatos;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class UtilidadesServidor {

    private static final Logger LOG = Logger.getLogger(UtilidadesServidor.class.getName());

    public static final String ETIQUETA_AGREGAR_SCRIPT = "@AGREGAR_SCRIPT ";
    public static final String ETIQUETA_AGREGAR_COLUMNA = "@AGREGAR_COLUMNA";
    public static final String ETIQUETA_AGREGAR_TABLA = "@AGREGAR_TABLA";
    public static final String ETIQUETA_VERSION = "VERSION_SISTEMA";
    public static final String ETIQUETA_MOSTRAR_ERROR = "MOSTRAR_ERROR";

    //Listado de conexiones en el Servidor
    public static List<String> hostConectados = new ArrayList<String>();

    //Interfaz para controlar el numero de pantallas conectadas
    public static ServidorMonitorUpdateInterface monitorUpdate;
    
    //Metodo que me sirve como proxi para coger los datos cargados previamente y no tener que estar haciendo la validacion siempre
    public static Map<Empresa,EmpresaLicencia> mapEmpresasLicencias=new HashMap<Empresa,EmpresaLicencia>();

    //public static TipoLicenciaEnum tipoLicenciaEnum;

    //public static String usuarioLicencia;

    //public static String pathRecursos;

    //public static Integer cantidadUsuarios;

    //public static List<ModuloCodefacEnum> modulosMap;

    /**
     * Querys que solo se ejecutaran en el modo de desarrollo Ejemplo: Querys de
     * datos para hacer pruebas con los modulos del sistema
     */
    public static InputStream[] queryDevelopment = { 
      RecursoCodefac.SQL.getResourceInputStream("insert_default_academico.sql"),
      RecursoCodefac.SQL.getResourceInputStream("insert_default_compras.sql"),
      RecursoCodefac.SQL.getResourceInputStream("insert_servicio.sql"),
    };

    public static InputStream[] querys = {
        RecursoCodefac.SQL.getResourceInputStream("function/funcion_normalizar_texto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_comprobante_fisico_disenio.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_academico.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_cliente.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_forma_pago.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_sri_identificacion.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_impuesto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_impuesto_detalle.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_impuesto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_parametro.sql"),
        //RecursoCodefac.SQL.getResourceInputStream("insert_parametros.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_impuesto_detalle.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_factura.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_retencion.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_nota_credito.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_cliente.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_perfil.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_acceso_directo.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_producto_proveedor.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_kardex.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_compra.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_bodega.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_categoria_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_catalogo_producto.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_cartera.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_default.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_empresa.sql"),
        RecursoCodefac.SQL.getResourceInputStream("insert_usuario.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_servicios.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_transporte.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_pos.sql"),
        RecursoCodefac.SQL.getResourceInputStream("create_modulo_restaurant.sql")
            
    };
    
     public static InputStream[] querys_update = {
         RecursoCodefac.SQL.getResourceInputStream("update/update.sql"),
         RecursoCodefac.SQL.getResourceInputStream("update/update_tipo_datos_decimal.sql"),
     };

    public static void crearBaseDatos() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión si no existe crea la base
            Connection conn = DriverManager.getConnection("jdbc:derby:Derby2.DB;databaseName=codefac;create=true;user="+AbstractFacade.usuarioDb);
            //Establecer autentificacion en derby
            Statement s = conn.createStatement();
            s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'true')");
            s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.authentication.provider', 'BUILTIN')");
            //System.out.println("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user."+AbstractFacade.usuarioDb+", '"+AbstractFacade.claveDb+"')");
            s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user."+AbstractFacade.usuarioDb+"','"+AbstractFacade.claveDb+"')");            
            s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.propertiesOnly', 'true')");
            s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.language.sequence.preallocator', '1')");

            if (conn != null) {

                /**
                 * Busca todos los querys disponibles para ejecutar
                 */
                for (InputStream query : querys) {
                    try {
                        String sql = UtilidadesTextos.getStringURLFile(query);
                        String[] sentencias = sql.split(";");
                        for (String sentencia : sentencias) {
                            LOG.log(Level.INFO, sentencia);
                            //Obtengo en bytes para transformar a utf 8 porque tenia problemas al insertar valores con acentos y ñ
                            byte ptext[] = sentencia.getBytes();
                            PreparedStatement pstm = conn.prepareStatement(new String(ptext, "UTF-8"));
                            //PreparedStatement pstm = conn.prepareStatement(sentencia);
                            pstm.execute();
                            pstm.close();
                        }
                    } catch (NullPointerException cpe) {
                        System.out.println("Alerta al crear el sql, Por favor revise que los sql no tengan espacios en blanco al final, apesar de esta advertencia el proceso puede continuar sin ningun problema");
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Solo ejecutar estos querys si el modo es desarrollo para hacer pruebas
                if (ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO)) {
                    for (InputStream query : queryDevelopment) //TODO: Optimizar el codigo para reuutilizar para los querys anteriores
                    {
                        String sql = UtilidadesTextos.getStringURLFile(query);
                        String[] sentencias = sql.split(";");
                        for (String sentencia : sentencias) {
                            //Obtengo en bytes para transformar a utf 8 porque tenia problemas al insertar valores con acentos y ñ
                            byte ptext[] = sentencia.getBytes();
                            PreparedStatement pstm = conn.prepareStatement(new String(ptext, "UTF-8"));
                            //PreparedStatement pstm = conn.prepareStatement(sentencia);
                            pstm.execute();
                            pstm.close();
                        }
                    }
                }

            }
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Actualiza los campos de una version anterior a los campos en las nuevas versiones
     */
    public static boolean actualizarBaseDatos(String versionPropiedades) {
        //Map donde almacenar todos los querys que se encuentre para actualiazar
        //Implemento un ordenador para que se ordene de la version mas antigua a la moderna
        TreeMap<String, List<ScriptCodefac>> mapQuerysVersion = new TreeMap<String, List<ScriptCodefac>>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return UtilidadesSistema.compareVersion(o1, o2);
            }
        });

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby:Derby2.DB;databaseName=codefac;user="+AbstractFacade.usuarioDb+";password="+AbstractFacade.claveDb);
            Statement s = conn.createStatement();

            if (conn != null) {
                /**
                 * Busca todos los querys disponibles para ejecutar
                 */
                for (InputStream query : querys) {
                    try {
                        String sql = UtilidadesTextos.getStringURLFile(query);
                        String[] sentencias = sql.split(";");
                        for (String sentencia : sentencias) {
                            //Obtengo en bytes para transformar a utf 8 porque tenia problemas al insertar valores con acentos y ñ
                            byte ptext[] = sentencia.getBytes();
                            String queryTabla = new String(ptext, "UTF-8");

                            //Buscar todas las etiquetas de agregar tablas
                            String[] etiquetasAgregarTabla = queryTabla.split(ETIQUETA_AGREGAR_TABLA);
                            if (etiquetasAgregarTabla.length > 1) {
                                for (int i = 1; i < etiquetasAgregarTabla.length; i++) {
                                    String etiqueta = etiquetasAgregarTabla[i];
                                    String version = obtenerPropiedad(etiqueta, ETIQUETA_VERSION);
                                    String queryNuevo = etiqueta.substring(etiqueta.indexOf("*/") + 2);

                                    //Agregar al Map los querys si no existe ninguno con ese numero de version
                                    if (mapQuerysVersion.get(version) == null) {
                                        List<ScriptCodefac> listaQuerys = new ArrayList<ScriptCodefac>();
                                        listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.CREATE_TABLE));
                                        mapQuerysVersion.put(version, listaQuerys);
                                    } else //Obtiene la lista de los querys anteriormente agregados para ingresar el nuevo query
                                    {
                                        List<ScriptCodefac> listaQuerys = mapQuerysVersion.get(version);
                                        listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.CREATE_TABLE));
                                        mapQuerysVersion.put(version, listaQuerys);
                                    }

                                }

                            }// else //Si no se crea la tabla en sus totalidad busco columnas para agregar , porque se supone que si crea la tabla no tiene sentido agregar columnas luego que van a generar error
                            //{
                             //Buscar todas las etiquetas de agregar columnas
                            String[] etiquetas = queryTabla.split(ETIQUETA_AGREGAR_COLUMNA);

                            // solo ingresar si encuentra divisiones que inidiquen que contega esa etiqueta
                            if (etiquetas.length > 1) {
                                for (int i = 1; i < etiquetas.length; i++) {
                                    String etiqueta = etiquetas[i];
                                    //Solo acceder a la parte donde tenga las etiquetas

                                    String version = obtenerPropiedad(etiqueta, ETIQUETA_VERSION);
                                    String nombreTabla = obtenerNombreTabla(queryTabla);
                                    String queryNuevo = obtenerQueryEdit(etiqueta, nombreTabla);

                                    //Agregar al Map los querys si no existe ninguno con ese numero de version
                                    if (mapQuerysVersion.get(version) == null) {
                                        List<ScriptCodefac> listaQuerys = new ArrayList<ScriptCodefac>();
                                        listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.INSERT_COLUMN));
                                        mapQuerysVersion.put(version, listaQuerys);
                                    } else //Obtiene la lista de los querys anteriormente agregados para ingresar el nuevo query
                                    {
                                        List<ScriptCodefac> listaQuerys = mapQuerysVersion.get(version);
                                        listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.INSERT_COLUMN));
                                        mapQuerysVersion.put(version, listaQuerys);
                                    }

                                }
                            }

                            //}

                        }
                    } catch (NullPointerException cpe) {
                        System.out.println("Alerta al crear el sql, Por favor revise que los sql no tengan espacios en blanco al final, apesar de esta advertencia el proceso puede continuar sin ningun problema");
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                for (InputStream queryStream : querys_update) {
                    String sql = UtilidadesTextos.getStringURLFile(queryStream);
                    String[] sentencias = sql.split(";");
                    for (String sentencia : sentencias) 
                    {
                        byte ptext[] = sentencia.getBytes();
                        String queryTabla = new String(ptext, "UTF-8");
                        //Agregar scripts pendientes para actualizar la base de datos
                        String[] etiquetas = queryTabla.split(ETIQUETA_AGREGAR_SCRIPT);
                        if (etiquetas.length > 1) {
                            for (int i = 0; i < etiquetas.length; i++) {
                                //Si la parte separada no tiene esta etiqueta no me sirve porque aveces se separa en etiquetas basura
                                //TODO: Revisar si esta solucion debe aplicar a los metodos anteriores
                                if(etiquetas[i].indexOf(ETIQUETA_VERSION)<0)continue;
                                
                                String etiqueta = etiquetas[i];
                                String version = obtenerPropiedad(etiqueta, ETIQUETA_VERSION);
                                String mostrarAdvertencia = obtenerPropiedad(etiqueta, ETIQUETA_MOSTRAR_ERROR);
                                //String nombreTabla = obtenerNombreTabla(queryTabla);
                                String queryNuevo = obtenerQuery(etiqueta);

                                //Agregar al Map los querys si no existe ninguno con ese numero de version
                                if (mapQuerysVersion.get(version) == null) {
                                    List<ScriptCodefac> listaQuerys = new ArrayList<ScriptCodefac>();
                                    listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.OTHER_SCRIPT,mostrarAdvertencia));
                                    mapQuerysVersion.put(version, listaQuerys);
                                } else //Obtiene la lista de los querys anteriormente agregados para ingresar el nuevo query
                                {
                                    List<ScriptCodefac> listaQuerys = mapQuerysVersion.get(version);
                                    listaQuerys.add(new ScriptCodefac(queryNuevo, ScriptCodefac.PrioridadQueryEnum.OTHER_SCRIPT,mostrarAdvertencia));
                                    mapQuerysVersion.put(version, listaQuerys);
                                }
                            }

                        }
                    }
                }

                /**
                 * Ejecutar todos los scripts pendientes en el map
                 */
                if (mapQuerysVersion.size() > 0) {
                    for (Map.Entry<String, List<ScriptCodefac>> entry : mapQuerysVersion.entrySet()) {
                        String versionGrabada = entry.getKey();
                        List<ScriptCodefac> lista = entry.getValue();

                        //Orden de mayor a menor para ejecutar en orden los scripts de mayor importancia
                        //TODO: Verificar esta funcion posteriormente porque actualmente no habian casos para revisar
                        Collections.sort(lista, new Comparator<ScriptCodefac>() {
                            @Override
                            public int compare(ScriptCodefac o1, ScriptCodefac o2) {
                                return o1.getPrioridad().compareTo(o2.getPrioridad());
                            }
                        });

                        //Solo ejecutar los scripts si la version para modificar es mayor la version actual, y menor e igual que la version a actualizar
                        if (UtilidadesSistema.compareVersion(versionGrabada, versionPropiedades) == 1 && UtilidadesSistema.compareVersion(versionGrabada, ParametrosSistemaCodefac.VERSION) <= 0) {
                            for (ScriptCodefac query : lista) {
                                LOG.log(Level.INFO, "Query Actualizado:" + query.getQuery());
                                try {
                                    PreparedStatement pstm = conn.prepareStatement(query.getQuery());
                                    pstm.execute();
                                    pstm.close();
                                } catch (SQLException ex) {
                                    LOG.log(Level.SEVERE, "Query Error:" + query.getQuery());
                                    if(query.isMostrarAdvertencia())
                                    {
                                        JOptionPane.showMessageDialog(null,"Error al actualizar la base de datos en el Query: \n"+query.getQuery()+"\n\n CAUSA:\n"+ex.getCause().toString(),"ERROR",JOptionPane.WARNING_MESSAGE);
                                    }
                                    Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }

                    }

                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UtilidadesServidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static String obtenerNombreTabla(String query) {
        //replaceAll("\\s","")
        String querySinEspacios = query.replaceAll("\\s", "").toLowerCase();

        int posicionCreacionTabla = querySinEspacios.indexOf("createtable") + ("createtable".length());

        String queryCortado = querySinEspacios.substring(posicionCreacionTabla);

        int posicionPrimerParatesis = queryCortado.indexOf("(");

        String nombreTabla = queryCortado.substring(0, posicionPrimerParatesis);

        return nombreTabla;

    }
    
    public static String obtenerQuery(String str)
    {
        //str=str.replace(" ","");//Copiar espacios en blanco
        int indiceTerminaEtiqueta=str.indexOf("*/");
        str=str.substring(indiceTerminaEtiqueta+2);
        return str;
    }

    public static String obtenerQueryEdit(String str, String nombreTabla) {
        //Todo : Buscar una metodologia para encontrar el nombre de la tabla que le contiene al Query
        //Procedimiento:
        //1.- quitar todos los espacios
        //2.- BUscar posicion de la palabra createtable
        //3-  Buscar posicion del primer parentesis despues de create table
        //4.- El nombre intermedio es el nombre de la tabla
        //5.- quitar porciacaso los espacios en blanco
        String queryEditar = "ALTER TABLE " + nombreTabla + " ADD";

        int posInicialParentesisPropiedades = str.indexOf(")"); //Buscar la primer parentesis de la etiqueta version del sistema (VERSION_SISTEMA) porque ya no necesito para encontrar el campo a editar
        String queryBuscar = str.substring(posInicialParentesisPropiedades + 1); //Elimino los datos con paratesis
        queryBuscar=queryBuscar.replace("*/",""); //Elimino este caracter al final de la etiqueta ( VERSION_SISTEMA) que corresponde a final el comentario

        int posFinalComaDelCampo = queryBuscar.indexOf(",");
        int posParentecisCerradoPropiedades = queryBuscar.indexOf(")"); //Esta parentesis es para verificar si existe algun tipo de dato que ocupa parentesis ejemplo VARCHAR(12)
        int posParentecisAbiertoPropiedades = queryBuscar.indexOf("(");

        //Si la coma esta entre un parentesis asumo que es para los tipo de datos decimal y debo buscar la siguiente ,
        if (posFinalComaDelCampo>posParentecisAbiertoPropiedades && posFinalComaDelCampo < posParentecisCerradoPropiedades) 
        {
            //Si entra en esta condicion significa que existe una segunda coma que es la que marca el final , esto puede suceder por ejemplo en el tipo de dato decimal que ocupa coma para definir            
            int posSegunaComa = queryBuscar.indexOf(",",posFinalComaDelCampo+1); //Busca el segundo coma que debe se el que divide el tipo de dato
            String queryTmp = queryBuscar.substring(0, posSegunaComa);
            queryEditar = queryEditar + queryTmp;

        } else {
            String queryTmp = queryBuscar.substring(0, posFinalComaDelCampo);
            queryEditar = queryEditar + queryTmp;
        }
        return queryEditar;
    }

    public static String obtenerPropiedad(String str, String propiedad) {
        //Posiciones de los parentesis
        int posInicial = str.indexOf("(");
        int posFinal = str.indexOf(")");

        String propiedadesStr = str.substring(posInicial + 1, posFinal);
        String[] propiedades = propiedadesStr.split(",");

        for (String prop : propiedades) {
            String[] dato = prop.split("=");
            String clave = dato[0];
            String valor = dato[1];

            if (clave.equals(propiedad)) {
                return valor;
            }
        }
        return "";
    }

}
