package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.dto.FeedDtos;
import hackathon.nomadworker.dto.ImageDtos.*;
import hackathon.nomadworker.service.FileUploadService;
import hackathon.nomadworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageUploadController {

    private final FileUploadService fileUploadService;
    private final UserService userService;

    @PutMapping("/api/user/profile")
    public PutResponse uploadImage(@RequestHeader("Authorization") String u_uid, @RequestParam MultipartFile file) {
        String imageUrl =  fileUploadService.uploadImage(file);
        User result = userService.userImagePost(u_uid, imageUrl);
        return new PutResponse("이미지 업로드 성공", 200, result.getU_image());
    }

    //@DeleteMapping("/api/user/image/delete")
    //public DeleteResponse deleteImage(@RequestHeader("Authorization") String u_uid, )
}
