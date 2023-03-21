package hackathon.nomadworker.domain.api;
import static hackathon.nomadworker.domain.dto.UserDtos.*;

import hackathon.nomadworker.domain.model.User_Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.service.AuthService;
import hackathon.nomadworker.domain.service.UserPlaceService;
import hackathon.nomadworker.domain.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController
{

    private final UserService userService;
    private final AuthService authService;

    private final UserPlaceService userPlaceService;

    @PostMapping(value="/api/user" , produces = "application/json;charset=UTF-8")
    public UserResultResponse userPost(@Valid @RequestBody UserPostRequest request) throws Exception {
        //토큰 발행
        String token= authService.createToken(request.getU_nickname());
        userService.userPost(token, request.getU_email(),request.getU_password(),request.getU_nickname());
        User user = userService.findOnebyToken(token);
        String message = "회원 등록이 완료되었습니다.";
        UserPostResponse data = new UserPostResponse(user.getId(), user.getU_image(), user.getU_nickname(),user.getU_uid(), user.getU_latitude(),user.getU_longitude());
        UserResultResponse userResultResponse = new UserResultResponse(message, 200, data);

        return userResultResponse;
    }

    @GetMapping(value="/api/users", produces = "application/json;charset=UTF-8")
    public UserResult userAll()
    {
        List<User> findUsers = userService.findUsers();
        List<UserDto> collect = findUsers.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
        return new UserResult(collect.size(), collect);
    }

    @GetMapping(value = "/api/user/nickname", produces = "application/json;charset=UTF-8")
    public NicknameSearchGetResponse NicknameSearch(@Param("userNickname") String userNickname)
    {
        List<User> users = userService.findOneByNickName(userNickname);
        List<UserDto> collect = users.stream().map(f -> new UserDto(f))
                .collect(Collectors.toList());

        NicknameSearchGetResponse result;

        if(!collect.isEmpty()) {
            result = new NicknameSearchGetResponse("중복된 닉네임 입니다", 400);
        }else { // 중복이 없으면 false
            result = new NicknameSearchGetResponse("사용가능한 닉네임 입니다", 200);
        }
        return result;

    }

    @PutMapping(value="api/user/geographic", produces = "application/json;charset=UTF-8")
    public UserCoordinatePutResponse usercoordinateupdate(@RequestHeader("Authorization") String u_uid,
                                                          @Valid @RequestBody UserCoordinatePutRequest request)
    {
        User result=userService.updateCoordinate(u_uid, request.getLatitude(),request.getLongitude());

        return new UserCoordinatePutResponse("갱신 성공", 200);
    }

    @PostMapping(value="/api/user/signin", produces = "application/json;charset=UTF-8")
    public UserSignInResponse SignIn(@Valid @RequestBody UserSignInRequest request) throws Exception {
        User user = userService.SignIn(request.getU_email(), request.getU_password());
        if(user == null) {
            UserPostResponse data = new UserPostResponse((long)0,null,null,null,(float)0.0,(float)0.0);
            return new UserSignInResponse("아이디 또는 비밀번호 불일치 계정을 확인해주세요.", 400, data);
        }
        else
        {
            UserPostResponse data = new UserPostResponse(user.getId(), user.getU_image(), user.getU_nickname(),user.getU_uid(), user.getU_latitude(),user.getU_longitude());
            return new UserSignInResponse("로그인 성공 !", 200, data);
        }
    }

    // 저정한 목록을 출력
    @GetMapping(value="/api/user/placesub", produces = "application/json;charset=UTF-8")
    public UserResultResponse getPlaceSub(@RequestHeader("Authorization") String u_uid,
                                          @RequestParam Long u_id)
    {
        if(Objects.equals(userService.findOnebyToken(u_uid).getId(), u_id))
        {

            List<User_Place> user_places=userPlaceService.findPlacesByUId(u_id);
            List<PlaceSubGetResponse> collect = user_places.stream()
                    .map(s -> new PlaceSubGetResponse(s))
                    .collect(Collectors.toList());
            if(collect.isEmpty()){
            return new UserResultResponse("장소 조회 성공",200,null);
            }
            else{
                return new UserResultResponse("장소 조회 성공",200,collect);
            }
        }
        else
        {
            return new UserResultResponse("장소 조회 실패", 400, null);
        }
    }
//  -- version 1 :
//        @PostMapping(value = "/api/user/placesub", produces = "application/json;charset=UTF-8")
//    public UserResponse postPlaceSub(@RequestHeader("Authorization") String u_uid,
//                                           @Valid @RequestBody PlaceSubPostRequest request)
//    {
//        if(Objects.equals(userService.findOnebyToken(u_uid).getId(), request.getU_id()))
//        {
//            if(userPlaceService.newUser_Place(request.getU_id(),request.getP_id()))
//            {
//                return new UserResponse("장소 등록 성공", 200);
//            }
//        }
//        return new UserResponse("장소 등록 실패", 400);
//
//    }
//
//    @DeleteMapping(value = "api/user/placesub")
//    public UserResponse deletePlaceSub(@RequestHeader("Authorization") String u_uid,
//                                       @RequestParam Long u_p_id)
//    {
//        if (userService.findOnebyToken(u_uid) != null)
//        {
//            userPlaceService.deleteBy(u_p_id);
//            return new UserResponse("장소 삭제 성공", 200);
//        } else
//        {
//            return new UserResponse("장소 삭제 실패", 400);
//        }
//    }
    // tmp version api
    @PostMapping(value = "/api/user/placesub", produces = "application/json;charset=UTF-8")
    public UserResponse postPlaceSub(@RequestHeader("Authorization") String u_uid,
                                           @Valid @RequestBody PlaceSubPostRequest request)
    {
        if(request.getU_p_scrab()) //scrabed already
        {
            userPlaceService.deleteByUidPid(request.getU_id(), request.getP_id());
            return new UserResponse("장소 등록 취소", 200);
        }
        else
        {
            userPlaceService.newUser_Place(request.getU_id(),request.getP_id());
            return new UserResponse("장소 등록 성공", 200);
        }

    }


}
