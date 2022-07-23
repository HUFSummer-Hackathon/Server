package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Feed;

import hackathon.nomadworker.domain.Place;

import hackathon.nomadworker.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class PlaceService {
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

}




