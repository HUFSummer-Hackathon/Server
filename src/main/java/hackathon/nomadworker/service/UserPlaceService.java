package hackathon.nomadworker.service;
import hackathon.nomadworker.domain.Place;
import hackathon.nomadworker.domain.User;
import hackathon.nomadworker.domain.User_Place;
import hackathon.nomadworker.repository.PlaceRepository;
import hackathon.nomadworker.repository.UserPlaceRepository;
import hackathon.nomadworker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPlaceService {
    private final UserPlaceRepository userPlaceRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public boolean newUser_Place(Long u_id,Long p_id)
    {
        if (!findUserPlaceByFidUid(u_id, p_id))
        {
            User user = userRepository.findOne(u_id);
            Place place = placeRepository.getPlacesById(p_id);
            userPlaceRepository.save(user, place);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void deleteBy(Long u_p_id)
    {
        userPlaceRepository.delete(u_p_id);
    }

    @Transactional(readOnly = true)
    public List<User_Place> findPlacesByUId(Long u_id)
    {
        return  userPlaceRepository.findPlacesByUserId(u_id);
    }


    @Transactional(readOnly = true)
    public boolean findUserPlaceByFidUid(Long u_id , Long p_id)
    {
        return userPlaceRepository.findUserPlaceByFidUid(u_id , p_id);
    }

}
