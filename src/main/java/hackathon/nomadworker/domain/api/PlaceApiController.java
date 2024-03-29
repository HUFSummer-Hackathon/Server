package hackathon.nomadworker.domain.api;
import hackathon.nomadworker.domain.model.Feed;
import hackathon.nomadworker.domain.model.Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.dto.PlaceDtos;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceByCategoryResponse;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceDetailDto;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceDto;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceDtoCat;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceDtoCoordinate;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceGradeResponse;
import hackathon.nomadworker.domain.dto.PlaceDtos.PlaceResultResponse;
import hackathon.nomadworker.domain.dto.PlaceDtos.placeRecommendDto;
import hackathon.nomadworker.domain.dto.PlaceDtos.placeSearchDto;
import hackathon.nomadworker.domain.dto.PlaceDtos.placeSearchOneByNameDto;
import hackathon.nomadworker.domain.dto.PlaceDtos.placecathomeresponse;
import hackathon.nomadworker.domain.service.PlaceService;
import hackathon.nomadworker.infra.aws.s3.FileUploadService;
import hackathon.nomadworker.domain.service.UserPlaceService;
import hackathon.nomadworker.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceApiController {

    private final PlaceService placeService;
    private final FileUploadService fileUploadService;
    private final UserService userService ;
    private final UserPlaceService userPlaceService;

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
    public PlaceResultResponse placeByCoordinateGet(@RequestHeader("Authorization") String u_uid, @RequestParam("placeId") Long p_id)
    {
        User user = userService.findOnebyToken(u_uid);
        Place place = placeService.findPlacesById(p_id);
        // use u_uid ->  to get info of scrab
        if (place == null )
        {
           return new PlaceResultResponse("장소 상세 조회 실패", 400, null);
        } else
        {
                if(userPlaceService.findUserPlaceByFidUid(user.getId(),p_id)) // 장소를 저장한 경우 .
                {
                    PlaceDetailDto result = new PlaceDetailDto(place, true);
                    return new PlaceResultResponse("장소 상세 조회 성공", 200, result);
                }
                else // 장소를 저장 하지 않은 경우 .
                {
                    PlaceDetailDto result = new PlaceDetailDto(place, false);
                    return new PlaceResultResponse("장소 상세 조회 성공", 200, result);
                }
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

    @PutMapping(value = "/api/place/grade", produces = "application/json;charset=UTF-8")
    public PlaceGradeResponse gradePlace(@RequestHeader("Authorization") String u_uid, @Valid @RequestBody PlaceDtos.PlaceGradeRequest request){
        placeService.gradePlace(request.getPlaceId(),request.getPlaceGrade());
        return new PlaceGradeResponse("등록 완료!",200);
    }

    @GetMapping(value = "/api/search/place",produces = "application/json;charset=UTF-8")
    public PlaceResultResponse searchPlace(@RequestHeader("Authorization") String u_uid,
                                           @Valid @RequestParam("place_cat") String p_cate,
                                           @RequestParam("place_Storetype") String p_storeType,
                                           @RequestParam("place_name") String p_name)
    {
        List<Place> places = placeService.searchPlace(p_cate, p_storeType, p_name);
        Place a = places.get(0);
        System.out.println(a.getP_addr());
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

    @PostMapping(value="/api/place/dataupload" , produces = "application/json;charset=UTF-8")
    public PlaceResultResponse placeDataUpload(
            @RequestParam MultipartFile file,@RequestParam String p_cate,@RequestParam String p_name,
            @RequestParam String p_weekt,@RequestParam String p_weekndt,
            @RequestParam String p_addr,@RequestParam String storeType, @RequestParam String rent_price
            )
    {
        String imageUrl = fileUploadService.uploadImage(file);
        try {
            // call image url first - >  multi part maybe  - >  get rturn image url
            PlaceDto result = new PlaceDto(placeService.newplace(p_cate, p_name, p_weekt, p_weekndt, p_addr, imageUrl, storeType, rent_price));

            return new PlaceResultResponse("upload", 200, result);
        }
        catch (Exception e)
        {
            fileUploadService.deleteFile(imageUrl);
            return new PlaceResultResponse("uploadfail", 400, null);
        }
    }

}
