package hackathon.nomadworker.service;

import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    public List<Place> findPlacesByCategory(String place_tag) {return placeRepository.findPlacesByCategory(place_tag);
    }



    public List<Place> findPlacesByCoordinate(float latitude,float longitude)
    {   // 반견 1km
        return placeRepository.getNearByCoordinate((double)latitude, (double)longitude,(double)1);
    }

    public List<Place> findPlacesall()
    {
        return  placeRepository.findAll();
    }


}




