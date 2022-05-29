/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface RubrosNivelServiceIf extends ServiceAbstractIf<RubrosNivel>{
    
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivel(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel) throws RemoteException;
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivelPeriodo(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel,Periodo periodo) throws RemoteException;
    public List<RubrosNivel> buscarPorCatalogoYNivel(CatalogoProducto catalogoProducto,Nivel nivel) throws RemoteException;
    public List<RubrosNivel> buscarPorCatalogo(CatalogoProducto catalogoProducto) throws RemoteException;
    public List<RubrosNivel> buscarPorPeriodoYMeses(Periodo periodo,CatalogoProducto catalogoProducto,List<RubroPlantillaMes> meses) throws RemoteException;
    public void eliminarRubroNivel(RubrosNivel rubrosNivel) throws RemoteException,ServicioCodefacException;
    public List<RubrosNivel> buscarPorPeriodoYNivel(Periodo p,Nivel nivel) throws RemoteException,ServicioCodefacException;
    public List<RubrosNivel> buscarPorPeriodoYCatalogo(Periodo p,CatalogoProducto catalogoProducto) throws RemoteException,ServicioCodefacException;
}
