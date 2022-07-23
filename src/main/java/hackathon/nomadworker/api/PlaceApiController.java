package hackathon.nomadworker.api;
import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.dto.PlaceDtos.*;
import hackathon.nomadworker.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.ArrayList;
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

        if (place == null )
        {
           return new PlaceResultResponse("장소 상세 조회 실패", 400, null);
        } else
        {
                PlaceDetailDto result = new PlaceDetailDto(place);
                return new PlaceResultResponse("장소 상세 조회 성공", 200, result);
        }

    }

    @GetMapping(value = "/api/place/category",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse placecathome(@RequestHeader("Authorization") String u_uid)
    {
        ArrayList<placecathomeresponse> arrayOfdata = new ArrayList<>();
        arrayOfdata.add(new placecathomeresponse("https://user-images.githubusercontent.com/51078673/179384322-884b8302-6c21-46ce-8d46-c29745796fcc.jpg",
                "서울"));
        arrayOfdata.add(new placecathomeresponse( "https://user-images.githubusercontent.com/51078673/179384435-2e54b08a-27a9-4f59-946a-77f110801914.png",
                "강릉"));

                arrayOfdata.add(new placecathomeresponse("https://user-images.githubusercontent.com/51078673/179384474-a48ca515-c4f8-4f99-bfa3-59655484e291.jpg",
                        "제주"));
                        arrayOfdata.add(new placecathomeresponse("https://user-images.githubusercontent.com/51078673/179384495-429fe737-6611-40d0-b81b-26ab615fc746.jpg",
                                "부산"));


        if (arrayOfdata == null )
        {
            return new PlaceResultResponse("근무 장소 카테고리 조회 실패", 400, null);
        } else
        {
                return new PlaceResultResponse("근무 장소 카테고리 조회 성공", 200, arrayOfdata);
        }

    }

    @GetMapping(value = "/api/search/place",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse searchPlace(@RequestHeader("Authorization") String u_uid,
                                           @Valid @RequestParam("place_cat") String p_cate,
                                           @RequestParam("place_Storetype") String p_storeType,
                                           @RequestParam("place_name") String p_name)
    {
        List<Place> places = placeService.searchPlace(p_cate, p_storeType, p_name);
        List<placeSearchDto> collect = places.stream()
                .map(p -> new placeSearchDto(p))
                .collect(Collectors.toList());
        return new PlaceResultResponse("장소 검색 성공", 200, collect);
    }

    @GetMapping(value = "/api/search/placetag",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse searchOneByName(@RequestHeader("Authorization") String u_uid,
                                               @Valid @RequestParam("placeName") String p_name)
    {
        List<Place> places = placeService.searchOnebyName(p_name);
        List<placeSearchOneByNameDto> collect = places.stream()
                .map(p -> new placeSearchOneByNameDto(p))
                .collect(Collectors.toList());
        return new PlaceResultResponse("검색 완료", 200, collect);
    }









}
