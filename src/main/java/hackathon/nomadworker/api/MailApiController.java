package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.MailDtos.*;
import hackathon.nomadworker.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class MailApiController
{
    private final MailService mailService;

@PostMapping(value="/api/mail" , produces = "application/json;charset=UTF-8")
    public MailPostResponse mailPost(@Valid  @RequestBody MailPostRequest request)
    {
        String address = request.getAddress();
        String randomcode = mailService.mailSend(address);
        String status = "전송 완료되었습니다";

        MailPostResponse mailPostResponse = new MailPostResponse(status,address,randomcode);

        System.out.println(mailPostResponse);

        return mailPostResponse;
    }


}
