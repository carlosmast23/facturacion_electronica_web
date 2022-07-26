/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.rmi;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author
 */
public abstract class UtilidadesRmi {
    
    public static byte[] serializar(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        byte[] serializedPrint = byteArrayOutputStream.toByteArray();
        return serializedPrint;
    }
    
    
    public static Object deserializar(byte[] objSerializado) throws IOException, ClassNotFoundException {
        
        if(objSerializado==null)
        {
            return null;
        }        
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objSerializado);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }
    
    public static byte[] serializarImg(BufferedInputStream input) throws IOException {
        DataInputStream dataInputStream = new DataInputStream((InputStream) input);
        ByteArrayOutputStream temporaryBuffer = new ByteArrayOutputStream( );
        copy(dataInputStream, new DataOutputStream(temporaryBuffer));
       
        byte[] serializedPrint = serializar(dataInputStream);
        return serializedPrint;
    }
    
    private static int copy(InputStream source, OutputStream destination) throws IOException {
        int nextByte;
        int numberOfBytesCopied = 0;
        while (-1 != (nextByte = source.read())) {
            destination.write(nextByte);
            numberOfBytesCopied++;
        }
        destination.flush();
        return numberOfBytesCopied;
    }


}
