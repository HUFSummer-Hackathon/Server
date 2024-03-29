package hackathon.nomadworker.domain.api;

import hackathon.nomadworker.domain.model.UserReply;
import hackathon.nomadworker.domain.model.Feed;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.model.UserLike;
import hackathon.nomadworker.domain.dto.FeedDtos.*;
import hackathon.nomadworker.domain.service.FeedService;
import hackathon.nomadworker.infra.aws.s3.FileUploadService;
import hackathon.nomadworker.domain.service.UserLikeService;
import hackathon.nomadworker.domain.service.UserReplyService;
import hackathon.nomadworker.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedApiController {

    private final FeedService feedService;
    private final UserService userService;
    private final FileUploadService fileUploadService;
    private final UserLikeService userLikeService;
    private final UserReplyService userReplyService;


    @PostMapping(value = "/api/feeds/one")
    public PostResponse uploadFeed(@RequestHeader("Authorization") String u_uid,
                                   @RequestParam MultipartFile file,
                                   @RequestParam String feed_content,
                                   @RequestParam String p_id)
    {
        Long place_id = Long.parseLong(p_id);
        String imageUrl = fileUploadService.uploadImage(file);

        if (imageUrl != null) {
            Date today = new Date();
            Locale currentLocale = new Locale("KOREAN", "KOREA");
            String pattern = "yyyyMMddHHmmss";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
            String time = formatter.format(today);
            feedService.feedPost(u_uid, feed_content,place_id, imageUrl, time);
            return new PostResponse("피드 작성 성공", 200);
        }
        else {
            return new PostResponse("피드 작성 실패", 400);
        }

    }
    // 피드 단일 조회
    @GetMapping(value = "api/feeds/one", produces = "application/json;charset=UTF-8")
    public FeedResultResponse feedUserOne(@RequestHeader("Authorization") String u_uid, @Param("f_id") Long f_id)
    {
        User findOnebyToken = userService.findOnebyToken(u_uid);
        Feed feedOne = feedService.findOne(f_id);

        List<UserLike> subsByFeedId = userLikeService.findUserLikesByFeedId(f_id);
        boolean like_status = false;

        if (f_id != null)
        {
            if (subsByFeedId.stream().anyMatch(s -> Objects.equals(s.getUser().getU_uid(), u_uid)))
            {
                like_status = true;
            }
            int count = userReplyService.findRepliesCountByFeedId(f_id);
            FeedOneDto feedOneDto = new FeedOneDto(feedOne, like_status,count);
            return new FeedResultResponse("단일 피드 조회 성공", 200, feedOneDto);
        }
        else
        {
            return new FeedResultResponse("단일 피드 조회 실패", 400, null);
        }
    }
    @DeleteMapping(value = "api/feeds/one", produces = "application/json;charset=UTF-8")
    public FeedResultResponseNoData deleteFeed(@RequestHeader("Authorization") String u_uid, @Param("f_id") Long f_id)
    {
        User findOnebyToken = userService.findOnebyToken(u_uid);
        Feed feedOne = feedService.findOne(f_id);
        log.info("!!!");
        String key = feedOne.getF_image().split(".com/")[1];
        if(fileUploadService.deleteFile(key)!=null)
        {
            feedService.deleteByFid(feedOne.getId());
            return new FeedResultResponseNoData("피드 삭제 완료!", 200);
        }
        return new FeedResultResponseNoData("피드 삭제 실패!", 400);
    }


    @GetMapping(value = "api/feeds/total", produces = "application/json;charset=UTF-8")
    public FeedResultResponse feedAll(@RequestHeader("Authorization") String u_uid)
    {
        List<Feed> feedAll = feedService.feedAll();
        boolean like_status = false;
        int r_count = 0 ;
        if (!feedAll.isEmpty()) {
            List<FeedDto> collect = feedAll.stream()
                    .map(f -> new FeedDto(f, like_status,r_count))
                    .collect(Collectors.toList());
            for (FeedDto i : collect)
            {
                if(userLikeService.checkUserFeedLike(u_uid, i.getF_id()) == true)
                {
                    i.setLike_status(true);
                }
                i.setR_count(userReplyService.findRepliesCountByFeedId(i.getF_id()));
            }
            return new FeedResultResponse("피드 불러오기 성공", 200, collect);
        }
        else {
            return new FeedResultResponse("피드 불러오기 살패", 400, null);
        }

    }

    @GetMapping(value = "api/feeds/usertotal", produces = "application/json;charset=UTF-8")
    public FeedResultResponse feedUserTotal(@RequestHeader("Authorization") String u_uid,@Param("u_id") Long u_id)
    {
        User feedUserTotal = feedService.feedUserTotal(u_id);
        if (feedUserTotal != null)
        {
            List<Feed> feed = feedUserTotal.getFeedList();
            if(!feed.isEmpty()) {
                ArrayList a = new ArrayList();
                for (Feed i : feed) {
                    FeedList feedlist = new FeedList(i);
                    a.add(feedlist);
                }
                FeedUserTotalDto collect = new FeedUserTotalDto(feedUserTotal, a);
                return new FeedResultResponse("유저 피드 전체 조회 성공", 200, collect);
            }
            else {
                FeedUserTotalDto collect = new FeedUserTotalDto(feedUserTotal,null);
                return new FeedResultResponse("유저 피드 전체 조회 성공", 200, collect);
            }

        } else {
            return new FeedResultResponse("유저 피드 전체 조회 실패 ", 400, null);
        }


    }


    @PostMapping(value = "/api/feeds/likes")
    public FeedResultResponseNoData feedUserlike(@RequestHeader("Authorization") String u_uid, @Valid @RequestBody FeedLikeRequest request) {
        User user = userService.findOnebyToken(u_uid);
        Long u_id = user.getId();
        Long f_id = request.getF_id();

        List<UserLike> subsByFeedId = userLikeService.findUserLikesByFeedId(f_id);
        long count = subsByFeedId.stream().count();

        if (f_id != null) {
            if (subsByFeedId.stream().anyMatch(s -> Objects.equals(s.getUser().getId(), u_id))) {
                //     "이미 좋아요를 하고 있습니다.";
                userLikePostDeleteResponse FeedresultResponse = new userLikePostDeleteResponse(count - 1, false);
                feedService.feedUserLikeUpdate(f_id, (int) (count - 1));
                // Delete
                userLikeService.deleteByUserFac(u_id, f_id);
                return new FeedResultResponseNoData("좋아요 취소", 200);
            }

            UserLike userLike = new UserLike();
            userLike.setUser(userService.findOne(u_id));
            userLike.setFeed(feedService.findOne(f_id));
            userLikeService.newUser_Like(userLike);
            userLikePostDeleteResponse FeedresultResponse = new userLikePostDeleteResponse(count + 1, true);
            feedService.feedUserLikeUpdate(f_id, (int) (count + 1));

            return new FeedResultResponseNoData("좋아요 성공", 200);
        } else {
            return new FeedResultResponseNoData("좋아요 실패", 400);
        }
    }
    @GetMapping(value = "api/feeds/reply")
    public FeedResultResponse feedUserreplyGet(@RequestHeader("Authorization") String u_uid,@RequestParam Long f_id)
    {
        User user = userService.findOnebyToken(u_uid);
        Feed feed = feedService.findOne(f_id);
        if (user.getId()!=0)
        {
            List<UserReply> User_Reply = userReplyService.findRepliesByFeedId(f_id);
            if (!User_Reply.isEmpty()) {
                List<ReplyResponseDto> collect = User_Reply.stream()
                        .map(r -> new ReplyResponseDto(r))
                        .collect(Collectors.toList());
                return new FeedResultResponse("댓글 조회 성공", 200, new GetReplyResponseDto(feed,collect));
            } else {
                return new FeedResultResponse("조회 성공 댓글 없음", 200,new GetReplyResponseDto(feed,null));
            }
        }else {
            return new FeedResultResponse("댓글 조회 실패", 400, null);
        }
    }
    @PostMapping(value = "api/feeds/reply")
    public FeedResultResponse feedUserreply(@RequestHeader("Authorization") String u_uid, @Valid @RequestBody NewReplyRequestDto request) {
        // respnse
        User user = userService.findOnebyToken(u_uid);
        if (Objects.equals(user.getId(), request.getU_id()))
        {
            UserReply userReply= userReplyService.newReply(request.getR_content(), request.getU_id(), request.getF_id(),request.getR_date());
            return new FeedResultResponse("댓글 추가 성공", 200, new PostReplyResponseDto(userReply));
            } else {return new FeedResultResponse("댓글 추가 실패", 400, null);
        }
    }

    @DeleteMapping(value = "api/feeds/reply")
    public PostResponse deletereply(@RequestHeader("Authorization") String u_uid, @Valid @RequestBody DeleteReplyRequestDto request)
    {
        // requset check
        UserReply user_reply = userReplyService.findOneByReplyId(request.getR_id());
        if (user_reply != null)
        {
            userReplyService.deleteByReplyId(user_reply.getId());
            return new PostResponse("댓글 삭제 성공",200);
        }else {
            return new PostResponse("댓글 삭제 실패",400);
        }
    }

}



