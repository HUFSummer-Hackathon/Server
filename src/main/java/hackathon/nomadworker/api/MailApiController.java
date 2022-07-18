package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.MailDtos.*;
import hackathon.nomadworker.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class MailApiController
{
    private final MailService mailService;

//@PostMapping(value="/api/mail" , produces = "application/json;charset=UTF-8")
//    public MailPostResponse mailPost(@Valid  @RequestBody MailPostRequest request)
//    {
//        String address = request.getAddress();
//        String randomcode = mailService.mailSend(address);
//        String status = "전송 완료되었습니다";
//
//        MailPostResponse mailPostResponse = new MailPostResponse(status,address,randomcode);
//
//        System.out.println(mailPostResponse);
//
//        return mailPostResponse;
//    }
@PostMapping(value="/api/mail" , produces = "application/json;charset=UTF-8")
    public MailPostResponse mailPost(@Param("userEmail") String userEmail)
    {

        if(! mailService.findOneByEmail(userEmail))
        {
            MailPostResponse mailPostResponse = new MailPostResponse("400", "이미존재하는 이메일입니다","0");
            return mailPostResponse;
        }else {
            String randomcode = mailService.mailSend(userEmail);

            MailPostResponse mailPostResponse = new MailPostResponse("200","가입 가능한 이메일 입니다",randomcode);
            return mailPostResponse;
        }

    }


}
