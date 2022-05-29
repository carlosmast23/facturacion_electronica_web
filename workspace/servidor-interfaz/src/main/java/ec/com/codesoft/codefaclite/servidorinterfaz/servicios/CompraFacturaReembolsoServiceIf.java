/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public interface CompraFacturaReembolsoServiceIf extends ServiceAbstractIf<CompraFacturaReembolso>{
    public List<CompraFacturaReembolso> buscarPorCompra(Compra compra)  throws ServicioCodefacException, RemoteException;
    
}
