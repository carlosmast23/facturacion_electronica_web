/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.archivos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Carlos
 */
public class UtilidadesZip {
    
    public static void comprimirToFile(String fileName, String outPath)
            throws IOException, FileNotFoundException {
        File file = new File(fileName);
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outPath + ".zip"));
        //Call recursion.

        recurseFiles(file, zos);
        //We are done adding entries to the zip archive,

        //so close the Zip output stream.
        zos.close();
    }
    
    public static InputStream comprimirToInputStream(String fileName)
            throws IOException, FileNotFoundException {
        File file = new File(fileName);
        
        //Archivo donde se va a generar el 
        ByteArrayOutputStream  out = new ByteArrayOutputStream();

        ZipOutputStream zos = new ZipOutputStream(out);
        //Call recursion.

        recurseFiles(file, zos);
        //We are done adding entries to the zip archive,

        //so close the Zip output stream.
        zos.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Recurses down a directory and its subdirectories to look for files to add
     * to the Zip. If the current file being looked at is not a directory, the
     * method adds it to the Zip file.
     */
    private static void recurseFiles(File file, ZipOutputStream zos)
            throws IOException, FileNotFoundException {
        if (file.isDirectory()) {
            //Create an array with all of the files and subdirectories         
            //of the current directory.
            String[] fileNames = file.list();
            if (fileNames != null) {
                //Recursively add each array entry to make sure that we get
                //subdirectories as well as normal files in the directory.
                for (int i = 0; i < fileNames.length; i++) {
                    recurseFiles(new File(file, fileNames[i]), zos);
                }
            }
        } //Otherwise, a file so add it as an entry to the Zip file.      
        else {
            byte[] buf = new byte[1024];
            int len;
            //Create a new Zip entry with the file's name.         

            ZipEntry zipEntry = new ZipEntry(file.toString());
            //Create a buffered input stream out of the file         

//we're trying to add into the Zip archive.         
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fin);
            zos.putNextEntry(zipEntry);
            //Read bytes from the file and write into the Zip archive.         

            while ((len = in.read(buf)) >= 0) {
                zos.write(buf, 0, len);
            }
            //Close the input stream.         

            in.close();
            //Close this entry in the Zip stream.         

            zos.closeEntry();
        }
    }
}
