package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Feed;

import hackathon.nomadworker.domain.Place;


import hackathon.nomadworker.dto.DownloadDtos;
import hackathon.nomadworker.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class PlaceService {

    private AsyncService asyncService;
    private final PlaceRepository placeRepository;

    public List<Place> findPlacesByCategory(String place_tag) {return placeRepository.findPlacesByCategory(place_tag);
    }



    public List<Place> findPlacesByCoordinate(float latitude,float longitude)
    {   // 반견 1km
        return placeRepository.getNearByCoordinate((double)latitude, (double)longitude,(double)3);
    }

    public List<Place> findPlacesall()
    {
        return  placeRepository.findAll();
    }

    public List<Feed> getRecommendPlace()
    {
        return placeRepository.getRecommendPlace();
    }

    public Place findPlacesById(Long p_id)
    {
        return  placeRepository.getPlacesById(p_id);
    }


    public List<Place> searchPlace(String p_cate, String p_storeType, String p_name)
    {
        List<Place> places = placeRepository.searchPlace(p_cate, p_storeType, p_name);
        return places;
    }

    public List<Place> searchOnebyName(String p_name)
    {
        return placeRepository.searchOneByName(p_name);
    }
/*
    public int gradePlaceCall(Long p_id, float p_grade){
        try {
            asyncService.run(() -> gradePlace(p_id, p_grade));
            return 200;
        }catch(NullServiceException sve){
            return 400;
        }
    }
    */

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int gradePlace(Long p_id, float p_grade){
        Place place = placeRepository.gradePlace(p_id);
        float placeGrade = place.getP_grade();
        Integer placeCount = place.getP_count();
        if(!(placeCount == 0)){
            placeGrade = (placeGrade*placeCount+p_grade) / (placeCount + 1);
            placeCount += 1;
        }
        else if(placeCount == 0){
            placeCount = 0;
            placeGrade = p_grade;
            placeCount += 1;
        }
        placeRepository.setgradePlace(p_id, placeGrade, placeCount);
        return 200;
    }


    public Void newplace(String searchaddress)
    {
        String url =  "https://www.google.co.kr/maps/search/";
        try {
            Connection.Response res = Jsoup.connect(url+searchaddress).followRedirects(true).execute();

            Document doc = res.parse();
            Elements metaTags = doc.getElementsByTag("meta");
            String linkOrigin = metaTags.get(10).attr("content");

            int s = linkOrigin.indexOf("=");
            int l = linkOrigin.indexOf("%");
            String longi =linkOrigin.substring(s+1,l);

            s = linkOrigin.indexOf("C");
            l = linkOrigin.indexOf("&");
            String lati =linkOrigin.substring(s+1,l);


        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }






}




