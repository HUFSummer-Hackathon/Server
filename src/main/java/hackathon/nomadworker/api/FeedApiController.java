package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.dto.FeedDtos.*;
import hackathon.nomadworker.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

    private final FeedService feedService;

    @GetMapping(value = "api/feeds/total", produces = "application/json;charset=UTF-8")
    public Result feedAll(){
        List<Feed> feedAll = feedService.feedAll();
        List<FeedDto> collect = feedAll.stream()
                .map(f -> new FeedDto(f))
                .collect(Collectors.toList());
        return new Result("피드 불러오기 성공", 200 , collect);
    }

    @GetMapping(value = "api/feeds/usertotal", produces = "application/json;charset=UTF-8")
    public Result feedUserTotal(@RequestHeader("Authorization") String u_uid){
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

}
