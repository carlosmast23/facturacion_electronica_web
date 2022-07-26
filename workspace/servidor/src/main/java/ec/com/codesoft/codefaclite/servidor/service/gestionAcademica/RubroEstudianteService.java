/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroEstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.AplicarDescuentoAcademicoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author
 */
public class RubroEstudianteService extends ServiceAbstract<RubroEstudiante, RubroEstudianteFacade> implements RubroEstudianteServiceIf {

    RubroEstudianteFacade rubroEstudianteFacade;

    public RubroEstudianteService() throws RemoteException {
        super(RubroEstudianteFacade.class);
        rubroEstudianteFacade = new RubroEstudianteFacade();
    }

    public void eliminar(RubroEstudiante entity) throws java.rmi.RemoteException
    {        
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaccion.commit();        
    }
    
    public List<RubroEstudiante> obtenerRubroMatriculaPorEstudianteInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estudianteInscrito",estudianteInscrito);
        mapParametros.put("rubroNivel.catalogoProducto.tipoCod",CatalogoProducto.TipoEnum.MATRICULA.getCodigo());
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }
    
    public List<RubroEstudiante> obtenerRubrosActivosPorEstudianteYEstadoFacturado(RubroEstudiante.FacturacionEstadoEnum estadoFacturadoEnum) throws RemoteException
    {
        return getFacade().getRubrosActivosPorEstudianteYEstadoFacturado(estadoFacturadoEnum);
    }

    /**
     * Obtener rubros activos por estudiante que aun no han sido pagados
     * @param estudianteInscrito
     * @return
     * @throws RemoteException 
     */
    public List<RubroEstudiante> obtenerRubrosActivosPorEstudiantesInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException {
        return getFacade().getRubrosActivosPorEstudiante(estudianteInscrito);
    }

    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) throws RemoteException {
        return getFacade().findRubrosEstudiantesPorRubros(rubros);
    }

    public void eliminarRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (RubroEstudiante rubrosEstudiante : rubrosEstudiantes) {
            //Solo elimina los rubros de los que esten sin facturar
            if (rubrosEstudiante.getEstadoFacturaEnum().equals(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR)) {
                rubrosEstudiante.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                rubrosEstudiante = entityManager.merge(rubrosEstudiante);
                entityManager.remove(rubrosEstudiante);
            }
        }
        transaccion.commit();
    }
    
    public void eliminarMesRubroPlantilla(RubroPlantillaMes rubroPlantillaMes) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException {
                try {
                    RubrosNivel rubroNivel=rubroPlantillaMes.getRubroNivel();
                    
                    if(rubroNivel==null)
                    {
                        RubroPlantillaMes entityPersistent=entityManager.merge(rubroPlantillaMes);
                        entityPersistent.getRubroPlantilla().getMesesGenerados().remove(entityPersistent); //Eliminar de la referencia de la lista
                        entityManager.remove(entityPersistent); //eliminar de la persistenca
                        
                        return ;//Si no estado atado a un rubro nivel entonces solo borro la referencia , pero al usuario le toca borrar manualmente las referencias creadas
                    }
                    
                    RubroEstudianteService servicio=new RubroEstudianteService();
                    
                    List<RubrosNivel> rubrosNivelList=new ArrayList<RubrosNivel>();
                    rubrosNivelList.add(rubroNivel);
                    
                    //Obtener todos los rubrosEstudiantes vinculados al rubro nivel creado por la plantilla
                    List<RubroEstudiante> rubrosEstudiante=servicio.obtenerRubrosEstudiantesPorRubros(rubrosNivelList);
                    
                    for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {
                        
                        //TODO: Ahorita solo estoy verificando que solo no tenga nnguna factura para anular , pero verificar si tambien me toca verificar el estado del rubro estudiante
                        if(rubroEstudiante.getEstadoFactura().equals(RubroEstudiante.FacturacionEstadoEnum.FACTURADO.getLetra()))
                        {
                            String mensajeException="No se puede procesar porque el estudiante :"+rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreSimple();
                            mensajeException+="\n con el rubro "+rubroNivel.getNombre()+" , porque se encuentra facturado";
                            throw new ServicioCodefacException(mensajeException); //Auotmaticamente genera el rollback cuando lanzo esta excepcion
                        }
                        //Cambiar el estado a eliminado del rubroEstudiante
                        rubroEstudiante.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        entityManager.merge(rubroEstudiante);
                    }
                    
                    //Eliminar el rubro del nivel generado
                    rubroNivel.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(rubroNivel);
                    
                    //Eliminar el rubro plantilla del mes
                    RubroPlantillaMes entityPersistent=entityManager.merge(rubroPlantillaMes);
                    entityPersistent.getRubroPlantilla().getMesesGenerados().remove(entityPersistent); //Eliminar de la referencia de la lista
                    entityManager.remove(entityPersistent); //eliminar de la persistenca
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(RubroEstudianteService.class.getName()).log(Level.SEVERE, null, ex);
                }

                    
        
            }
        });
        
    }

    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla, MesEnum mesEnum, String nombreRubroMes,Integer anio,DescuentoAcademico descuentoAcademico,AplicarDescuentoAcademicoEnum aplicarDescuentoEnum) throws RemoteException,ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                /**
                 * =============================================================
                 *      CALCULAR EL TEMA DE PORCENTAJES DE DESCUENTOS
                 * ============================================================
                 */
                BigDecimal porcentajeDescuento=descuentoAcademico.getPorcentaje();
                
                //Crear el rubro nivel de esa plantilla
                RubrosNivel rubroNivel = new RubrosNivel();
                rubroNivel.setCatalogoProducto(rubroPlantilla.getCatalogoProducto());
                rubroNivel.setDiasCredito(rubroPlantilla.getDiasCredito());
                rubroNivel.setNivel(null);
                rubroNivel.setNombre(nombreRubroMes);
                rubroNivel.setPeriodo(rubroPlantilla.getPeriodo());
                rubroNivel.setValor(rubroPlantilla.getValor());
                rubroNivel.setMesNumero(mesEnum.getNumero());
                rubroNivel.setAnio(anio);
                rubroNivel.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                rubroNivel.setDescuentoPorcentaje(porcentajeDescuento);
                //rubroNivel.setReferenciaPlantilla(rubroPlantilla);

                entityManager.persist(rubroNivel);

                for (RubroPlantillaEstudiante estudiateInscrito : rubroPlantilla.getDetalles()) {
                                 
                    //Si por algun motivo no tiene ligado un estuidante inscrito no ejecuto ninguna accion
                    if(estudiateInscrito.getEstudianteInscrito()==null)
                    {
                        continue;
                    }
                    
                    
                    //Cuando el estudiante inscrito esta con estado inactivo no genera la deuda para ese estudiante
                    if(!estudiateInscrito.getEstudianteInscrito().getEnumEstado().equals(GeneralEnumEstado.ACTIVO))
                    {
                        continue; //Pasa al siguiente registro
                    }

                    //Si existen los valores del estudiante ingresado son los mismos , si no existen obtengo la valor general
                    BigDecimal valorDeudaEstudiante=(estudiateInscrito.getValorPlantilla()!=null)?estudiateInscrito.getValorPlantilla():rubroNivel.getValor();

                    //Si el valor de la deuda esta ingresada como 0 entonces no grabo
                    if(valorDeudaEstudiante.compareTo(BigDecimal.ZERO)==0)
                    {
                        continue;
                    }


                    RubroEstudiante rubroEstudiante = new RubroEstudiante();

                    rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                    rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                    rubroEstudiante.setEstudianteInscrito(estudiateInscrito.getEstudianteInscrito());
                    rubroEstudiante.setRubroNivel(rubroNivel);
                    
                    BigDecimal descuentoEstudiante=calcularDescuentoFinalEstudiante(estudiateInscrito, descuentoAcademico, aplicarDescuentoEnum);
                    rubroEstudiante.setProcentajeDescuento((descuentoEstudiante!=null)?descuentoEstudiante.intValue():0);
                    
                    //Obtener el valor menos descuento porque finalmente ese es el valor que debemos cobrar
                    BigDecimal valorNeto=UtilidadesPorcentajes.calcularValorMenosDescuento(valorDeudaEstudiante,new BigDecimal(rubroEstudiante.getProcentajeDescuento()));                    
                    rubroEstudiante.setValorDescuento(valorDeudaEstudiante.subtract(valorNeto));
                    rubroEstudiante.setSaldo(valorNeto);
                    rubroEstudiante.setValor(valorNeto);
                    
                    entityManager.persist(rubroEstudiante);
                }

                //Grabar los valores del mes que se estan generando
                RubroPlantillaMes rubroPlantillaMes=new RubroPlantillaMes();
                rubroPlantillaMes.setAnio(anio);
                rubroPlantillaMes.setNumeroMes(mesEnum.getNumero());
                rubroPlantillaMes.setRubroPlantilla(rubroPlantilla);
                rubroPlantillaMes.setRubroNivel(rubroNivel);
                entityManager.persist(rubroPlantillaMes);

                rubroPlantilla.addMesGenerado(rubroPlantillaMes);

                //Actualizar el rubroPlantillaMes
                entityManager.merge(rubroPlantilla);


                //return rubroPlantilla;
            }
        });
        
       
        return rubroPlantilla;
    }
    
    /**
     * Calcula los descuentos para los diferentes casos cuando se generan las deduas desde las plantillas
     * @param estudiateInscrito
     * @param descuentoAcademico
     * @param aplicarDescuentoEnum
     * @return 
     */
    private BigDecimal calcularDescuentoFinalEstudiante(RubroPlantillaEstudiante estudiateInscrito,DescuentoAcademico descuentoAcademico,AplicarDescuentoAcademicoEnum aplicarDescuentoEnum)
    {
        //verificar primero si tiene un porcentaje asigando la plantilla o directamente devuelvo el descuento por estudiante
        if (descuentoAcademico.getPorcentaje().compareTo(BigDecimal.ZERO) == 0) {
            return estudiateInscrito.getDescuentoPlantilla();
        }

        BigDecimal porcentajeDescuentoGeneral = descuentoAcademico.getPorcentaje();
        switch (aplicarDescuentoEnum) {
            /**
             * FORMULA PARA CALCULAR EL DESCUENTO ACUMULADO
             * d1= descuento 1
             * d2= descuento 2
             * porcentajeTotalEntero=d1+d2 -(d2*d1/100)
             */
            case FUSIONAR_DESCUENTO:
                if (estudiateInscrito.getDescuentoPlantilla()!=null && estudiateInscrito.getDescuentoPlantilla().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal descuentoTotal=porcentajeDescuentoGeneral.add(estudiateInscrito.getDescuentoPlantilla());
                    descuentoTotal=descuentoTotal.subtract(porcentajeDescuentoGeneral.multiply(estudiateInscrito.getDescuentoPlantilla()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
                    return descuentoTotal;
                } else {
                    //Si no tiene descuentos el estudiante devuelvo solo el descuento global
                    return porcentajeDescuentoGeneral;
                }

            case APLICAR_SOLO_DESCUENTO_ACTUAL_SI_TIENE_OTROS_DESCUENTOS:
                return porcentajeDescuentoGeneral;

            case NO_APLICAR_DESCUENTO_SI_YA_TIENE_ASIGANDO_DESCUENTO:
                if(estudiateInscrito.getDescuentoPlantilla()==null || estudiateInscrito.getDescuentoPlantilla().compareTo(BigDecimal.ZERO)==0)
                {
                    return porcentajeDescuentoGeneral;
                }
                else
                {
                    return estudiateInscrito.getDescuentoPlantilla();
                }
        }
        return BigDecimal.ZERO;
        
    }
    
    public void actualizarRubrosEstudiante(List<RubroEstudiante> rubroEstudiantes) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    for (RubroEstudiante rubroEstudiante : rubroEstudiantes) {                    
                        entityManager.merge(rubroEstudiante);
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubroEstudianteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException ,ServicioCodefacException{
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //TODO: Agregar validacion para evitar ingresar rubros de estudiantes duplicados
                
                for (EstudianteInscrito estudiante : estudiantes) {
                    RubroEstudiante rubroEstudiante = new RubroEstudiante();
                    rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                    rubroEstudiante.setEstudianteInscrito(estudiante);
                    rubroEstudiante.setSaldo(rubroNivel.getValor());
                    rubroEstudiante.setValor(rubroNivel.getValor());
                    rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                    rubroEstudiante.setRubroNivel(rubroNivel);

                    entityManager.persist(rubroEstudiante);
                }
                entityManager.flush();

            }
        });
        //EntityTransaction transaccion = getTransaccion();
        //transaccion.begin();
        
    }

    public void crearRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (RubroEstudiante rubroEstudiante : rubrosEstudiantes) {
            //Solo valido que creen rubros que no exitan es decir que no tengan id
            if(rubroEstudiante.getId()==null)
            {
                rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                entityManager.persist(rubroEstudiante);
            }
        }
        entityManager.flush();
        transaccion.commit();

    }

    @Override
    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante,Periodo periodo) throws RemoteException {
        return rubroEstudianteFacade.obtenerDeudasEstudiante(estudiante,periodo);
    }

    @Override
    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo,Date fechaInicio,Date fechaFin) throws RemoteException {
        return rubroEstudianteFacade.obtenerRubroPeriodoGrupo(periodo,fechaInicio,fechaFin);
    }

    public List<RubroEstudiante> buscarRubrosMes(EstudianteInscrito est,Periodo periodo, CatalogoProducto catalogoProducto, List<RubroPlantillaMes> meses) throws RemoteException 
    {
        return rubroEstudianteFacade.buscarRubrosMes(est,periodo, catalogoProducto, meses);
    }
    
    public Long contarRubrosEstudiantePorRubroNivel(RubrosNivel rubroNivel) throws RemoteException
    {
        return getFacade().contarRubrosEstudiantePorRubroNivelFacade(rubroNivel);
    }
    
    public List<RubroEstudiante> consultarPorEstudianteInscritoSinFacturar(EstudianteInscrito estudianteInscrito) throws RemoteException
    {
        return getFacade().consultarPorEstudianteInscritoSinFacturarFacade(estudianteInscrito);
    }
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivel(EstudianteInscrito estudianteInscrito, RubrosNivel rubroNivel) throws ServicioCodefacException, RemoteException {
        Map<String, Object> mapParametro = new HashMap<String, Object>();
        mapParametro.put("estudianteInscrito", estudianteInscrito);
        mapParametro.put("rubroNivel", rubroNivel);

        return getFacade().findByMap(mapParametro);
    }
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivelActivos(EstudianteInscrito estudianteInscrito, RubrosNivel rubroNivel) throws ServicioCodefacException, RemoteException {
        //RubroEstudiante R;
        //R.getEstadoEnum()
        Map<String, Object> mapParametro = new HashMap<String, Object>();
        mapParametro.put("estudianteInscrito", estudianteInscrito);
        mapParametro.put("rubroNivel", rubroNivel);
        mapParametro.put("estado",GeneralEnumEstado.ACTIVO.getEstado());

        return getFacade().findByMap(mapParametro);
    }

}
