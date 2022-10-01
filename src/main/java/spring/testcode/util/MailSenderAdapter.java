package spring.testcode.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class MailSenderAdapter implements MailSender {

    private Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean sender() {
        return mail.sendMail();
    }
}
