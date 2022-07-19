package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.MailDtos.*;
import hackathon.nomadworker.service.MailService;
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
    public MailPostResponse mailPost(@Param("userEmail") String userEmail)
    {

        if(! mailService.findOneByEmail(userEmail))
        {   Map<String, String> data = new HashMap<>();
            data.put("code","0");
            MailPostResponse mailPostResponse = new MailPostResponse("이미존재하는 이메일입니다",400,data);
            return mailPostResponse;
        }else {
            String randomcode = mailService.mailSend(userEmail);
            Map<String, String> data = new HashMap<>();
            data.put("code",randomcode);
            MailPostResponse mailPostResponse = new MailPostResponse("가입 가능한 이메일 입니다",200,data);
            return mailPostResponse;
        }

    }


}
