package hackathon.nomadworker.api;


import hackathon.nomadworker.dto.MailDtos;

import hackathon.nomadworker.service.MailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class MailApiController
{
    private final MailService mailService;

    @PostMapping(value="api/mail" , produces = "application/json;charset=UTF-8")
    public MailDtos.MailPostResponse MailPostResponse(@RequestBody MailDtos.MailPostRequest request)
    {
        String address = request.getAddress();
        String randomcode = mailService.mailSend(address);
        String status = "전송 완료되었습니다.";
        MailDtos.MailPostResponse mailPostResponse = new MailDtos.MailPostResponse(status,address,randomcode);
        System.out.println("send----ok");
        return mailPostResponse;
    }


}
