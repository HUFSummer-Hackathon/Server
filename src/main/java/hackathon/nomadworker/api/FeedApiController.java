package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.dto.FeedDtos.*;
import hackathon.nomadworker.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new Result(collect.size(), collect);
    }
}
