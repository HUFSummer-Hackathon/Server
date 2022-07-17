package hackathon.nomadworker.api;

import static hackathon.nomadworker.dto.UserDtos.*;

import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController
{

    private final UserService userService;


    @PostMapping(value="/api/user" , produces = "application/json;charset=UTF-8")
    public UserPostResponse userPost(@Valid @RequestBody UserPostRequest request)
    {
        userService.userPost(request.getU_uid(), request.getU_email(),request.getU_password(),request.getU_nickname(), request.getU_image(), request.getU_latitude(), request.getU_longitude());
        String status = "회원 등록이 완료되었습니다.";
        UserPostResponse userPostResponse = new UserPostResponse(status);
        return userPostResponse;
    }

    @GetMapping(value="/api/users", produces = "application/json;charset=UTF-8")
    public UserResult userAll()
    {
        List<User> findUsers = userService.findUsers();
        List<UserDto> collect = findUsers.stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
        return new UserResult(collect.size(), collect);
    }

}
