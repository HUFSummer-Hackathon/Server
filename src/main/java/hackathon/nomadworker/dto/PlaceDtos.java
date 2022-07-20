package hackathon.nomadworker.dto;


import hackathon.nomadworker.domain.Place;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PlaceDtos
{
    @Data
    @AllArgsConstructor
    public static class PlaceResultResponse<T>
    {
    private String message;
    private int status;
    private T data;
    }

    @Data @AllArgsConstructor
    public static class PlaceByCategoryResponse<T>
    {
        private String place_tag;
        private T place;
    }


    @Data
    public static class PlaceDtoCat
    {
        private long p_id;
        private String p_name;
        private String p_addr;
        private String p_weekt;
        private String p_weekndt;
        private String p_image;

        public PlaceDtoCat(Place place)
        {
            this.p_id = place.getId();
            this.p_name = place.getP_name();
            this.p_addr =place.getP_addr();
            this.p_weekt = place.getP_weekt();
            this.p_weekndt = place.getP_weekndt();
            this.p_image = place.getP_image();
        }
    }
}
