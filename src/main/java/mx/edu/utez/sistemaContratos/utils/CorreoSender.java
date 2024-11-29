package mx.edu.utez.sistemaContratos.utils;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CorreoSender {
    private final static Logger logger = LoggerFactory.getLogger(CorreoSender.class);

    @Autowired
    private static JavaMailSender correoSender;

    String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
    }

    public static void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = correoSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htlmMsg = "<h3 style='color:rgb(0,46,93);'>Sistema de Prueba</h3><br>" +
                    text + "<br><hr style='border-color:rgb(0,171,132);'/><p style='text-align:justify;'>Si no solicitaste este cambio, ignora el correo.</p>" +
                    "<p style='text-align:justify;'>Esta cuenta de correo no es supervisada, no responder este mensaje.</p>";

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htlmMsg, true);
            correoSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("No se pudo enviar el correo");
        }
    }
}