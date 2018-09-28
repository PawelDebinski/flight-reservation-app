package pl.pawel.flightreservation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
@PropertySource("classpath:myconfig.properties")
public class EmailUtil {
    
    @Autowired
    private JavaMailSender sender;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    @Value("${pl.pawel.flightreservation.itinerary.email.subject}")
    private String EMAIL_SUBJECT;

    @Value("${pl.pawel.flightreservation.itinerary.email.body}")
    private String EMAIL_BODY;

    @Value("${pl.pawel.flightreservation.itinerary.email.sender}")
    private String EMAIL_SENDER;

    public void sendItinerary(String toAddress, String filePath) {
        LOGGER.info("=== Inside sendItinerary() -> toAddress: {}, filePath: {}", toAddress, filePath);
        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(EMAIL_SENDER);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject(EMAIL_SUBJECT);
            messageHelper.setText(EMAIL_BODY);
            messageHelper.addAttachment("Itinerary", new File(filePath));
            sender.send(message);

        } catch (MessagingException e) {
            LOGGER.error("Exception inside sendItinerary() " + e);
        }
    }
}
