package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Feed;

import hackathon.nomadworker.domain.Place;

import hackathon.nomadworker.dto.PlaceDtos;
import hackathon.nomadworker.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.hibernate.service.NullServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;


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



}




