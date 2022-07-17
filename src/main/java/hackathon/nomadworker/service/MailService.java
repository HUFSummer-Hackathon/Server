package hackathon.nomadworker.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;
@Service
@AllArgsConstructor
public class MailService
{
    private JavaMailSender javaMailSender;

    public String mailSend(String adddress)
    {
        SimpleMailMessage  message = new SimpleMailMessage();
        message.setTo(adddress);

        message.setSubject("회원가입 인증 번호입니다");

        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        String code =Integer.toString(rand.nextInt(888888)+111111);
        message.setText(code);

        javaMailSender.send(message);

        return code;
    }

}
