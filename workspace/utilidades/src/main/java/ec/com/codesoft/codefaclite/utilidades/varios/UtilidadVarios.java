/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *TODO:Cambiar el nombre de esta clase y dale un solo objetico que es para temas de la tarjeta de Red
 * @author Carlos
 */
public abstract class UtilidadVarios {
    
    private static String[] excepcionesMac={
        "Virtual Ethernet Adapter",
        "VirtualBox Host-Only Ethernet Adapter",
        "Microsoft Wi-Fi Direct Virtual Adapter",
        "Bluetooth Device (Personal Area Network)"};
    
    
    public static void abrirArchivo(String path)
    {
        try {
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static  String obtenerMac()
    {
        NetworkInterface a;
        String linea;
        try {
            //a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = a.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String obtenerIpServidor()
    {
        Vector<String> excepcionesMac=new Vector<String>();
        excepcionesMac.add("VirtualBox Host-Only Ethernet Adapter");
        excepcionesMac.add("Microsoft Wi-Fi Direct Virtual Adapter");
        excepcionesMac.add("Bluetooth Device (Personal Area Network)");
        
        try {
            final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) 
            {
                NetworkInterface networkInterface=e.nextElement();
                final byte [] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++)
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    
                    String nombreInterface=networkInterface.getName().toLowerCase();
                    int indiceNet=nombreInterface.indexOf("eth");
                    int indiceWlan=nombreInterface.indexOf("wlan");
                    int indiceEnp=nombreInterface.indexOf("enp"); //Interfaces centos
                    if((indiceNet>=0 || indiceWlan>=0 || indiceEnp>=0) && !excepcionesMac.contains(networkInterface.getDisplayName()))
                    {
                        System.out.println(networkInterface.getDisplayName());
                        System.out.println(networkInterface.getName());
                        System.out.println(sb.toString());
                        //Enumeration<InetAddress> addresses = iface.getInetAddresses();
                        Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
                        
                        while (inetAddress.hasMoreElements()) 
                        {
                            InetAddress currentAddress=inetAddress.nextElement();                                                       
                            if (currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress()) {
                                //System.out.println(currentAddress);
                                return currentAddress.getHostAddress();
                            }                            
                            //currentAddress = inetAddress.nextElement();
                        }
                        //eturn currentAddress.getHostAddress();
                    }
                }
                //break;
            }
        } catch (SocketException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "localhost";
    }
    
    public static  List<InterfazRed> obtenerMacSinInternet(String interfazRed)
    {
        List<InterfazRed> resultadoList=new ArrayList<InterfazRed>();
        try {
            /**
             * =================================================================================
             * Como primera opcion trato de encontrar una mac previamente ingresada con el nombre por defecto
             * =================================================================================
             */
            if(interfazRed!=null && !interfazRed.isEmpty())
            {
                NetworkInterface interfazEncontrada=NetworkInterface.getByName(interfazRed);
                if(interfazEncontrada!=null)
                {
                    InterfazRed resultado=buscarInterfazValida(interfazEncontrada);
                    if(resultado!=null)
                    {
                        resultadoList.add(resultado);
                    }
                }
            }
            
            /**
             * =====================================================================================
             * Posteriormente agrego otra interfaces validas si por algun motivo cambia el nombre de la interfaz de red por defecto
             * =====================================================================================
             */
             List<InterfazRed> interfacesValidas=obtenerInterfazValidas();
             for (InterfazRed interfacesValida : interfacesValidas) 
             {
                 resultadoList.add(interfacesValida);
                //return interfacesValida.mac;
            }
        } catch (SocketException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoList;
        
    }
    
    public static List<InterfazRed> obtenerInterfazValidas() throws SocketException
    {
        List<InterfazRed> interfacesRed=new ArrayList<InterfazRed>();
        final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            //NetworkInterface networkInterface = e.nextElement();
            InterfazRed resultado = buscarInterfazValida(e.nextElement());
            if (resultado != null) {
                interfacesRed.add(resultado);
            }
            //break;
        }
        return interfacesRed;
    }
    
    private static InterfazRed buscarInterfazValida(NetworkInterface networkInterface) throws SocketException
    {        
        final byte[] mac = networkInterface.getHardwareAddress();
        if (mac != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            String nombreInterface = networkInterface.getName().toLowerCase();
            int indiceNet = nombreInterface.indexOf("eth");
            int indiceWlan = nombreInterface.indexOf("wlan");
            int indiceEn = nombreInterface.indexOf("en");
            if ((indiceNet >= 0 || indiceWlan >= 0 || indiceEn>=0 ) && validarExcepcionesInterfazRed(networkInterface.getDisplayName(),excepcionesMac)) {
                System.out.println("============= INTERFAZ VALIDA ===================");
                System.out.println(networkInterface.getDisplayName());
                System.out.println(networkInterface.getName());
                System.out.println(sb.toString());
                return new InterfazRed(sb.toString(),networkInterface.getName());
                //return sb.toString();
            }else
            {
                /*System.err.println("XXXXXXXXXXXXXX INTERFAZ NO VALIDA XXXXXXXXXXXXXXXXX");
                System.out.println(networkInterface.getDisplayName());
                System.out.println(networkInterface.getName());
                System.out.println(sb.toString());*/
            }
        }
        return null;
    }
    
    private static Boolean validarExcepcionesInterfazRed(String nombre,String[] excepcionesMac)
    {
        for (String excepcion : excepcionesMac) {
            if(nombre.indexOf(excepcion)>=0)
            {
                return false;
            }
        }
        return true;
    }
    
    
    public static String getStringHtmltoUrl(InputStream input)
    {
        try {
            InputStreamReader reader = new InputStreamReader(input,"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            String htmlText = "";
            while ((line = br.readLine()) != null) {
                htmlText += line;
            }
            return htmlText;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadVarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
