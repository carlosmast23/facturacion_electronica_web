/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.Mensaje;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.Writer;

/**
 *
 * @author
 */
public class XStreamUtil {

	public static XStream getRespuestaXStream()
	{
		XStream xstream = new XStream(new XppDriver()
		{
			public HierarchicalStreamWriter createWriter(Writer out)
			{
				return new PrettyPrintWriter(out)
				{
					protected void writeText(QuickWriter writer, String text)
					{
						writer.write(text);
					}
				};
			}
		});
		xstream.alias("respuesta", RespuestaComprobante.class);
		xstream.alias("autorizacion", Autorizacion.class);
		xstream.alias("fechaAutorizacion", XMLGregorianCalendarImpl.class);
		xstream.alias("mensaje", Mensaje.class);
		xstream.registerConverter(new RespuestaDateConverter());

		return xstream;
	}


}
