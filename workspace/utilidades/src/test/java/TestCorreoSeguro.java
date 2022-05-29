
import java.util.Properties;
import javax.mail.Message;
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
public class TestCorreoSeguro {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "carlosmast2302@gmail.com";
    private static final String SMTP_AUTH_PWD  = "code17bwbtj";

    public static void main(String[] args) throws Exception{
       new TestCorreoSeguro().test();
    }

    public void test() throws Exception{
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Testing SMTP-SSL");
        message.setContent("This is a test", "text/plain");
        message.setFrom(new InternetAddress(SMTP_AUTH_USER));

        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress("elvis@presley.org"));

        transport.connect
          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
