package checksites;

import org.apache.log4j.Logger;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EMailSender {

    protected static final Logger log = Logger.getRootLogger();

    public static void sendMail(String mailSubject, String mailText, String[] emailList) {

        try {
            String hostMail = XmlSites.getHostMail();
            //String fromMail = XmlSites.getNameSender();
            String toMail = getEmailList(emailList);

            Session session = createSmtpSession(hostMail);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("checksites@bssys.com"));
            message.addRecipients(Message.RecipientType.TO, toMail);
            message.setSubject(mailSubject);
            message.setText(mailText);

            Transport.send(message);
            log.info("Рассылка сообщений выполнена. В потоке: " + Thread.currentThread().getName());
        } catch (MessagingException me) {
            log.error("Ошибка рассылки сообщений: " + me + " В потоке: " + Thread.currentThread().getName());
        }
    }

    private static Session createSmtpSession(String hostMail) {
        final Properties props = new Properties();
        props.setProperty("mail.smtp.host", hostMail);
        //props.setProperty("mail.smtp.auth", "true");
        //props.setProperty("mail.smtp.port", "" + 25);
        //props.setProperty("mail.smtp.starttls.enable", "false");
        //props.setProperty("mail.debug", "true");

        return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        });
    }

    private static String getEmailList(String[] eMail) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < eMail.length; ++i) {
            list.append(eMail[i]).append(", ");
        }
        return list.toString();
    }
}