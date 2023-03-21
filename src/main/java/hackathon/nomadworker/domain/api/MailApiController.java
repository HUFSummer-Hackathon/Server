package hackathon.nomadworker.domain.api;
import hackathon.nomadworker.domain.dto.MailDtos.MailResultResponse;
import hackathon.nomadworker.domain.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class MailApiController
{
    private final MailService mailService;
    @GetMapping(value="/api/user/mail" , produces = "application/json;charset=UTF-8")
    public MailResultResponse mailPost(@Param("userEmail") String userEmail)
    {

        if(! mailService.findOneByEmail(userEmail))
        {   Map<String, String> data = new HashMap<>();
            data.put("code","0");
            MailResultResponse mailResultResponse = new MailResultResponse("이미존재하는 이메일입니다",400,data);
            return mailResultResponse;
        }else {
            String randomcode = mailService.mailSend(userEmail);
            Map<String, String> data = new HashMap<>();
            data.put("code",randomcode);
            MailResultResponse mailResultResponse = new MailResultResponse("가입 가능한 이메일 입니다",200,data);
            return mailResultResponse;
        }

    }


}
