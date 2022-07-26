/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ReferidoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteReferidos;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.util.Vector;

/**
 *
 * @author
 */
public class ReferidosReporteModel extends FacturaReporteModel{

    public ReferidosReporteModel() {
        super();        
        valoresIniciales();
    }

    private void valoresIniciales() {
        
        filtrarReferidos=true; //Variable para que solo filtre los que tenga algun referido
        ///Para el reporte de facturacion no me importa que sean visibles estos campos del referido
        getLblReferido().setVisible(true);
        getTxtReferido().setVisible(true);
        getBtnBuscarReferido().setVisible(true);
        getChkTodosReferidos().setVisible(true);
        getChkReporteAgrupadoReferido().setVisible(true);
        setTitle("Referidos Reporte");
    }

    @Override
    protected void listenerChecks() {
        super.listenerChecks();        
        getChkTodosReferidos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    referido = null;
                    getTxtReferido().setText("");
                    //getLblNombreCliente().setText("..");
                    getBtnBuscarReferido().setEnabled(false);
                } else {
                    getBtnBuscarReferido().setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void listenerBotones() {
        super.listenerBotones(); //To change body of generated methods, choose Tools | Templates.
        getBtnBuscarReferido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ReferidoBusquedaDialogo busquedaDialogo = new ReferidoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
                buscarDialogoModel.setVisible(true);
                referido = (Persona) buscarDialogoModel.getResultado();
                if (referido != null) {
                    getTxtReferido().setText(referido.getIdentificacion()+" - "+referido.getNombresCompletos());
                }
            }
        });
        
    }


    @Override
    protected Vector<String> crearCabezeraTabla() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Preimpreso");
        titulo.add("Referencia");
        titulo.add("Fecha");
        titulo.add("Identificación");
        titulo.add("Razón social");
        titulo.add("Referido");
        titulo.add("Documento");
        titulo.add("Estado");
        titulo.add("Tipo");
        titulo.add("Subtotal 12%");
        titulo.add("Subtotal 0% ");
        titulo.add("Descuentos");
        titulo.add("IVA 12%");
        titulo.add("Valor Afecta");
        titulo.add("Total");
        return titulo;
    }

    @Override
    public ControladorReporteFactura crearControlador() {
        return new ControladorReporteReferidos(session.getEmpresa());
    }
    
    
    
}
