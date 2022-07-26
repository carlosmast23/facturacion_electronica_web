/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.email;

/**
 *
 * @author
 */
public class PropiedadCorreo {
    
    /**
     * Datos por defecto
     */
    public static final PropiedadCorreo GMAIL=new PropiedadCorreo("smtp.gmail.com",587);
    public static final PropiedadCorreo YAHOO=new PropiedadCorreo("smtp.mail.yahoo.com",587);
    public static final PropiedadCorreo HOTMAIL=new PropiedadCorreo("smtp-mail.outlook.com",587);
    public static final PropiedadCorreo LIVE=new PropiedadCorreo("smtp.live.com",587);
    
    
    private String host;
    private Integer port;

    
    public PropiedadCorreo(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    
    public static PropiedadCorreo obtenerPropiedadesPorDefecto(String email)
    {
        String emailFormat=email.toLowerCase();
        if(emailFormat.indexOf("@gmail")>=0)
        {
            return PropiedadCorreo.GMAIL;
        }
        else
        {
            if(email.indexOf("@hotmail")>=0)
            {
                return PropiedadCorreo.HOTMAIL;
            }
            else
            {
                if(email.indexOf("@outlook")>=0)
                {
                    return PropiedadCorreo.HOTMAIL;
                }
                else
                {
                    if(email.indexOf("@yahoo")>=0)
                    {
                        return PropiedadCorreo.YAHOO;
                    }
                    else
                    {
                        if(email.indexOf("@live")>=0)
                        {
                            return PropiedadCorreo.LIVE;
                        }
                    }
                
                }
            }
        }
        return null;
    }
        
}
