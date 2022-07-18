package hackathon.nomadworker.api;

import static hackathon.nomadworker.dto.UserDtos.*;

import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.dto.UserDtos;
import hackathon.nomadworker.dto.authDtos.*;
import hackathon.nomadworker.service.AuthService;
import hackathon.nomadworker.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import software.amazon.ion.NullValueException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController
{

    private final UserService userService;
    private final AuthService authService;


    @PostMapping(value="/api/user" , produces = "application/json;charset=UTF-8")
    public UserPostResponse userPost(@Valid @RequestBody UserPostRequest request) throws Exception {

        //토큰 발행
        String token= authService.createToken(request.getU_nickname());

        userService.userPost(token, request.getU_email(),request.getU_password(),request.getU_nickname());
        String msg = "회원 등록이 완료되었습니다.";
        UserPostResponse userPostResponse = new UserPostResponse(msg, "200", token);
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

    @GetMapping(value = "/api/user/nicknamecheck", produces = "application/json;charset=UTF-8")
    public NicknameSearchGetResponse NicknameSearch(@Param("userNickname") String userNickname)
    {
        List<User> facility1 = userService.findOneByNickName(userNickname);
        List<UserDto> collect = facility1.stream()
                .map(f -> new UserDto(f))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return new NicknameSearchGetResponse(true,collect.size(), collect);
        }else { // 중복이 없으면 true

            return new NicknameSearchGetResponse(false,collect.size(), collect);
        }

    }

}
