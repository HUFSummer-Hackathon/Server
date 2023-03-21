package hackathon.nomadworker.domain.api;

import hackathon.nomadworker.domain.dto.ImageDtos.PutResponse;
import hackathon.nomadworker.domain.model.User;
;
import hackathon.nomadworker.infra.aws.s3.FileUploadService;
import hackathon.nomadworker.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageUploadController {

    private final FileUploadService fileUploadService;
    private final UserService userService;

    @PutMapping(value = "/api/user/profile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PutResponse uploadImage(@RequestHeader("Authorization") String u_uid,
                                   @RequestPart(required = false) MultipartFile image)
    {
        String imageUrl =  fileUploadService.uploadImage(image);
        User result = userService.userImageUpdate(u_uid, imageUrl);
        if(result!= null) {
            return new PutResponse("이미지 갱신 성공", 200);
        }else
        {
            return new PutResponse("이미지 갱신 실패", 400);
        }
    }

}
