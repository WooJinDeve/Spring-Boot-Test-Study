package spring.testcode.util;

import org.springframework.stereotype.Component;

@Component
public class MailSendStub implements MailSender{

    @Override
    public boolean sender() {
        return true;
    }
}
