/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class DialogoCopiarArchivos {
    
    public Path origen = null;
    public Path destino = null;
    private JFileChooser jFileChooser;

    public DialogoCopiarArchivos(String titulo,String nombreFormato,String... formatos) 
    {
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle(titulo);
        jFileChooser.setFileFilter(new FileNameExtensionFilter(nombreFormato,formatos));        
    }
    
    @Deprecated //TODO: Metodo que solo sirve para mover dentro del mismo servidor una imagen
    public void establecerDondeMoverArchivo(String rutaArchivo, String rutaDestino) 
    {
        this.origen = FileSystems.getDefault().getPath(rutaArchivo);
        this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }
    
    public boolean moverArchivo() 
    {
        File file = destino.toFile();
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }

        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            //getTxtNombreFirma().setText("" + destino.getFileName());
            
        } catch (IOException ex) {
            ex.printStackTrace();            
            return false;
        }
        return true;
    }
    
    public File abrirDialogo() {
        int seleccion = jFileChooser.showDialog(null, "Abrir");
        switch (seleccion) {
            case JFileChooser.APPROVE_OPTION:
                return jFileChooser.getSelectedFile();
                
            case JFileChooser.CANCEL_OPTION:

                break;
            case JFileChooser.ERROR_OPTION:

                break;
        }
        return null;
    }

    
}
