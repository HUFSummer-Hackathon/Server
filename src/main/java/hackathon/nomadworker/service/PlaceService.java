package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Feed;

import hackathon.nomadworker.domain.Place;


import hackathon.nomadworker.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
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

    /**
     * @param p_cate
     * @param name
     * @param p_weekt
     * @param p_weekndt
     * @param p_addr
     * @param imageurl
     * @param storeType
     * @param rent_price
     * @return  P_addr 을 이용한 위도 경도 좌표를 포함한 객체 db 에 저장후 반환
     */
    @Transactional
    public Place newplace(String p_cate,String name,String p_weekt,String p_weekndt,String p_addr,String imageurl,String storeType,String rent_price)
    {
        Place place = new Place();
        place.setP_cate(p_cate);
        place.setP_name(name);
        place.setP_weekt(p_weekt);
        place.setP_weekndt(p_weekndt);
        place.setP_grade((float) 0.0);
        place.setP_count(0);

        place.setP_addr(p_addr);
        place.setP_image(imageurl);

        place.setP_storeType(storeType);
        place.setRent_price(rent_price);

        // make  instance  of Place
        // call codordinate
        String getcoordinate = getcoordinate(p_addr);

        String[] coorarr = getcoordinate.split(" ");


        float flongi = Float.parseFloat(coorarr[0]);
        float flati = Float.parseFloat(coorarr[1]);

        place.setP_latitude(flati);
        place.setP_longitude(flongi);

        String pointg = String.format("POINT(%s %s)",coorarr[0],coorarr[1]);
        try {
            Point pointinput = (Point) new WKTReader().read(pointg);
            place.setP_gpoint(pointinput);
            return placeRepository.post(place);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param searchaddress
     * @return String : longitude+" " +latitude
     */
    public String getcoordinate(String searchaddress)
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


            return  longi+" "+lati ;
        } catch (
                IOException e)
        {
            throw new RuntimeException(e);
        }

    }




}




