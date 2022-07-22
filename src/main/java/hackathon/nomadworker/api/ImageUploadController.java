package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.dto.FeedDtos;
import hackathon.nomadworker.dto.ImageDtos.*;
import hackathon.nomadworker.service.FileUploadService;
import hackathon.nomadworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageUploadController {

    private final FileUploadService fileUploadService;
    private final UserService userService;

    @PostMapping("/api/user/image")
    public PostResponse uploadImage(@RequestHeader("Authorization") String u_uid, @RequestPart MultipartFile file) {
        String imageUrl =  fileUploadService.uploadImage(file);
        User result = userService.userImagePost(u_uid, imageUrl);
        return new PostResponse("이미지 업로드 성공", 200, result.getU_image());
    }
}
