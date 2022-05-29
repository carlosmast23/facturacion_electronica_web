/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.util.Log;

/**
 *
 * @author Carlos
 */
public class ServidorSMS implements Runnable{

    private static final Logger LOG = Logger.getLogger(ServidorSMS.class.getName());
    /*
    Variable que me indica la cantidad maxima de caracteres que puede enviar en un mensaje de texto
    */
    //public static final int LIMITE_CARACTERES=160;
    
    
    private int puerto;
    private ServerSocket servidorSocket;
    private List<Socket> clientesConectados;
    private Thread hilo;
    
    private static ServidorSMS instance;
    
    public static ServidorSMS getInstance()
    {
        if(instance==null)
        {
            instance=new ServidorSMS();
        }
        return instance;
    }
    
    public ServidorSMS() 
    {
        puerto=ParametrosSistemaCodefac.PUERTO_APP_MOVIL_SMS; //Obtener el puerto por el que se va a comunicar el sistema
        this.clientesConectados=new ArrayList<Socket>();
        //iniciarServidor();
    }
    
    public void iniciarServidor()
    {
        try {
            //InetAddress addr = InetAddress.getByName("192.168.1.3");
            servidorSocket = new ServerSocket(puerto);
            hilo=new Thread(this); //Iniciar el escucha de peticiones para conectarse al servidor
            hilo.start();
            LOG.log(Level.INFO,"Iniciado servicio SMS CODEFAC , PUERTO:"+servidorSocket.getLocalPort()+"IP: "+servidorSocket.getInetAddress().getHostAddress());
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void esperarConexion()
    {
        try {
            Socket socket=servidorSocket.accept();
            clientesConectados.add(socket);
            
            LOG.log(Level.INFO,"Nuevo celular conectado: "+socket.getLocalAddress().getLocalHost());
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean enviarMensaje(String numero,String mensaje)
    {
        if(!clientesConectados.isEmpty())
        {
            Socket socketCliente=null;
            try {
                socketCliente=clientesConectados.get(0);
                socketCliente.setSoTimeout(1000*10); //Establece el tiempo de espera maximo para recibir una confirmacion del celular como 10 segundos 
                String numeroCompleto=ParametrosSistemaCodefac.CODIGO_TELEFONO_ECUADOR+numero.substring(1);
                escribirSocket(numeroCompleto, socketCliente);
                escribirSocket(mensaje, socketCliente);
                //Leer respuesta del servidor para saber si el proceso fue enviado correctamente
                leerSocket(socketCliente); //TODO:Falta implementar la lectura de codigos de error pero por ahora me conforma cuando lanza las excepciones
                LOG.log(Level.INFO,"Mensaje SMS enviado Número: "+numeroCompleto+", Mensaje:"+mensaje);
                return true;
                
            } catch (SocketException ex) {
                clientesConectados.remove(socketCliente); //Si sucede algun error de conexion 
                Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                clientesConectados.remove(socketCliente); //Si sucede algun error de conexion elimino la conexion actual
                Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    private String leerSocket(Socket socketCliente) throws IOException
    {
        String resultado="";
        try {
            BufferedReader input=new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            resultado=input.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
            throw ex; //Lanzar la excepcion para que controle el flujo superior
        }
        return resultado;
    }
    
    private void escribirSocket(String mensaje,Socket socketCliente)
    {
        PrintStream output = null;
        try {
            output = new PrintStream(socketCliente.getOutputStream());
            output.println(mensaje);

        } catch (IOException ex) {
            Logger.getLogger(ServidorSMS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //output.close();
        }
    }

    @Override
    public void run() {
        //Metodo que siempre esta esperando conexiones de clientes al servidor
        while(true)
        {
            esperarConexion();
        }
    }
    
    /**
     * Metodo que permite verificar si el servicio esta dicponible para proceder a enviar el mensaje
     * @return 
     */
    public Boolean servicioDisponible()
    {
        if(clientesConectados.size()>0)
            return true;
        
        return false;
    }
    
}
