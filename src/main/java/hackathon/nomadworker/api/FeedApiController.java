package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.dto.FeedDtos.*;
import hackathon.nomadworker.service.FeedService;
import hackathon.nomadworker.service.FileUploadService;
import hackathon.nomadworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

    private final FeedService feedService;
    private final UserService userService;
    private final FileUploadService fileUploadService;

    @PostMapping(value = "/api/feeds/new")
    public PostResponse uploadFeed(@RequestHeader("Authorization") String u_uid, @RequestParam MultipartFile file,
    @RequestParam String feed_content, @RequestParam Long p_id) {
        String imageUrl = fileUploadService.uploadImage(file);
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        String time = formatter.format(today);
        feedService.feedPost(u_uid, feed_content, p_id, imageUrl, time);
        return new PostResponse("피드 작성 성공", 200);
    }

    @GetMapping(value = "api/feeds/total", produces = "application/json;charset=UTF-8")
    public Result feedAll(@RequestHeader("Authorization") String u_uid)
    {
        List<Feed> feedAll = feedService.feedAll();
        List<FeedDto> collect = feedAll.stream()
                .map(f -> new FeedDto(f))
                .collect(Collectors.toList());
        return new Result("피드 불러오기 성공", 200 , collect);
    }

    @GetMapping(value = "api/feeds/usertotal", produces = "application/json;charset=UTF-8")
    public Result feedUserTotal(@RequestHeader("Authorization") String u_uid)
    {
        User feedUserTotal = feedService.feedUserTotal(u_uid);
        List<Feed> feed = feedUserTotal.getFeedList();
        ArrayList a = new ArrayList();
        for(Feed i : feed)
        {
            FeedList feedlist = new FeedList(i);
            a.add(feedlist);
        }
        FeedUserTotalDto collect = new FeedUserTotalDto(feedUserTotal ,a);

        return new Result("유저 피드 전체 조회 성공", 200 , collect);

    }

    @GetMapping(value = "api/feeds/one", produces = "application/json;charset=UTF-8")
    public Result feedUserOne(@RequestHeader("Authorization") String u_uid, @Param("f_id") Long f_id)
    {
        User findOnebyToken = userService.findOnebyToken(u_uid);
        Feed feedUserOne = feedService.feedUserOne(u_uid, f_id);
        FeedOneDto feedOneDto = new FeedOneDto(findOnebyToken, feedUserOne);
        return new Result("단일 피드 조회 성공", 200, feedOneDto);
    }

}
