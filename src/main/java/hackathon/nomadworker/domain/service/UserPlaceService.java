package hackathon.nomadworker.domain.service;
import hackathon.nomadworker.domain.model.Place;
import hackathon.nomadworker.domain.model.User;
import hackathon.nomadworker.domain.model.UserPlace;
import hackathon.nomadworker.domain.repository.PlaceRepository;
import hackathon.nomadworker.domain.repository.UserPlaceRepository;
import hackathon.nomadworker.domain.repository.UserRepository;
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
    public void deleteByUPID(Long u_p_id)
    {
        userPlaceRepository.delete(u_p_id);
    }

    /**
     *
     * @param u_id
     * @param p_id
     */
    @Transactional
    public void deleteByUidPid(Long u_id ,Long p_id )
    {
        userPlaceRepository.deleteUidPid(u_id,p_id);
    }

    @Transactional(readOnly = true)
    public List<UserPlace> findPlacesByUId(Long u_id)
    {
        return  userPlaceRepository.findPlacesByUserId(u_id);
    }

    /**
     *
     * @param u_id
     * @param p_id
     * @return if user subscribe place  return true  else : false
     */
    @Transactional(readOnly = true)
    public boolean findUserPlaceByFidUid(Long u_id , Long p_id)
    {
        return userPlaceRepository.findUserPlaceByFidUid(u_id , p_id);
    }

}
