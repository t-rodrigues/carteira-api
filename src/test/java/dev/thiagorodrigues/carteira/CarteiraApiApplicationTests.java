package dev.thiagorodrigues.carteira;

import dev.thiagorodrigues.carteira.infra.mail.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CarteiraApiApplicationTests {

    @MockBean
    private MailService mailService;

    @Test
    void contextLoads() {
    }

}
