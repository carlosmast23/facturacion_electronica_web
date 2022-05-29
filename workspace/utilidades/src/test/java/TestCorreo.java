
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Carlos
 */
public class TestCorreo {

    public static void main(String[] args) {

       // CorreoElectronico correo=new CorreoElectronico("carlosmast2302@gmail.com","especodesoft2301","Esto es un ejemplo","carlosmast2301@hotmail.es","ejemplo");
        //correo.sendMail();
    }

    public static void enviar2() {
        try {
            String smtpServer = "smtp.gmail.com";
            int port = 587;
            final String userid = "carlosmast2302@gmail.com";//change accordingly
            final String password = "especodesoft2301";//change accordingly
            String contentType = "text/html";
            String subject = "test: bounce an email to a different address "
                    + "from the sender";
            String from = "youraddress@gmail.com";
            String to = "bouncer@fauxmail.com";//some invalid address
            String bounceAddr = "toaddress@gmail.com";//change accordingly
            String body = "Test: get message to bounce to a separate email address";

            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", "587");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.from", bounceAddr);

            Session mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userid, password);
                }
            });

            MimeMessage message = new MimeMessage(mailSession);
            message.addFrom(InternetAddress.parse(from));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(body, contentType);

            Transport transport = mailSession.getTransport();
            try {
                System.out.println("Sending ....");
                transport.connect(smtpServer, port, userid, password);
                transport.sendMessage(message,
                        message.getRecipients(Message.RecipientType.TO));
                System.out.println("Sending done ...");
            } catch (Exception e) {
                System.err.println("Error Sending: ");
                e.printStackTrace();

            }
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(TestCorreo.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
