/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.tareasProgramadas;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.RespaldosModelUtilidades;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class RespaldoProgramadoTarea implements Runnable
{    
    
    public RespaldoProgramadoTarea() {
    }
    
    
    @Override
    public void run() {        
        generarRespaldoBaseDatosPorCorreo();
        Logger.getLogger(RespaldoProgramadoTarea.class.getName()).log(Level.INFO,"Generado RESPALDO AUTOMATICO desde la tarea programada");
    }
    
    /**
     * TODO: Mejorar el tema de obtener cualquier empresa por que se ve forzado esta parte
     * el motivo que necesita una empresa es para buscar las credenciales par aluego 
     */
    private void generarRespaldoBaseDatosPorCorreo()
    {
        try {                        
            //Buscar cualquier empresa por defecto por el momento
            Empresa empresaSeleccionada=null;
            List<Empresa> empresaList=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE);
            if(empresaList.size()>0)
            {
                empresaSeleccionada=empresaList.get(0);
            }
            
            if(empresaSeleccionada!=null)
            {
                try {
                    RespaldosModelUtilidades.generarRespaldoUbicacion(true,empresaSeleccionada,null,false);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(RespaldoProgramadoTarea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldoProgramadoTarea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
