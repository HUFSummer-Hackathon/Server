package hackathon.nomadworker.api;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.Menu;
import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.dto.PlaceDtos.*;
import hackathon.nomadworker.service.PlaceService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;

    @GetMapping(value="/api/place/location" , produces = "application/json;charset=UTF-8")
    public PlaceResultResponse placeByCatagoryGet(@RequestHeader("Authorization") String u_uid,
                                                  @RequestParam("locationCategory") String p_cate) {
        List<Place> places = placeService.findPlacesByCategory(p_cate);
        if (places.isEmpty())
        {
            return new PlaceResultResponse("지역별 근무 장소 조회 실패", 400,null);
        } else {
            List<PlaceDtoCat> collect = places.stream().map(place -> new PlaceDtoCat(place)).collect(Collectors.toList());
            PlaceByCategoryResponse placeByCategoryResponse = new PlaceByCategoryResponse(p_cate, collect);
            return new PlaceResultResponse("지역별 근무 장소 조회 성공", 200, placeByCategoryResponse);
        }

    }
    @GetMapping(value = "/api/place/near", produces = "application/json;charset=UTF-8")
    public PlaceResultResponse placeByCoordinateGet(@RequestHeader("Authorization") String u_uid,
                                                    @RequestParam("latitude") float latitude,
                                                    @RequestParam("longitude") float longitude) {
        List<Place> places = placeService.findPlacesByCoordinate(latitude,longitude);
        if (places.isEmpty()) {
            return new PlaceResultResponse("근처 장소 조회 실패", 400, null);
        } else {
            List<PlaceDtoCoordinate> collect = places.stream().map(place -> new PlaceDtoCoordinate(place)).collect(Collectors.toList());
            return new PlaceResultResponse("근처 장소 조회 성공", 200, collect);
        }
    }

    // 개발용 장소 전체 조회
    @GetMapping(value = "/api/places",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse plaecallGet() {
        List<Place> places = placeService.findPlacesall();
        if (places.isEmpty()) {
            return new PlaceResultResponse("장소 조회 실패", 400, null);
        } else {
            List<PlaceDto> collect = places.stream().map(place -> new PlaceDto(place)).collect(Collectors.toList());
            return new PlaceResultResponse(" 장소 조회 성공", 200, collect);
        }
    }

    @GetMapping(value = "api/place/recommend", produces = "application/json;charset=UTF-8")
    public PlaceResultResponse place() {
        List<Feed> recommend = placeService.getRecommendPlace();
        List<placeRecommendDto> collect = recommend.stream()
                .map(f -> new placeRecommendDto(f))
                .collect(Collectors.toList());
        return new PlaceResultResponse("추천 근무 장소 조회 성공",200,collect);
    }

    @GetMapping(value = "/api/place/detail",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse placeByCoordinateGet(@RequestHeader("Authorization") String u_uid,
                                                    @RequestParam("placeId") Long p_id)
    {
        Place place = placeService.findPlacesById(p_id);
        List <Menu> menus= placeService.placeMenuAll(p_id);
        List<menuDto> collect = menus.stream().map(menu -> new menuDto(menu)).collect(Collectors.toList());

        if (place == null )
        {
           return new PlaceResultResponse("장소 상세 조회 실패", 400, null);
        } else
        {
            if (collect.size()==0)
            {
                PlaceDetailDto result = new PlaceDetailDto(place, null);
                return new PlaceResultResponse("장소 상세 조회 성공", 200, result);
            }
            else {

                PlaceDetailDto result = new PlaceDetailDto(place,collect);
                return new PlaceResultResponse("장소 상세 조회 성공", 200, result);
            }
        }

    }
}
