package hackathon.nomadworker.dto;

import hackathon.nomadworker.domain.Feed;
import hackathon.nomadworker.domain.Menu;
import hackathon.nomadworker.domain.Place;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PlaceDtos {
    @Data
    @AllArgsConstructor
    public static class PlaceResultResponse<T> {
        private String message;
        private int status;
        private T data;
    }

    @Data
    @AllArgsConstructor
    public static class PlaceByCategoryResponse<T> {
        private String place_tag;
        private T place;
    }


    @Data
    public static class PlaceDtoCat {
        private long p_id;
        private String p_name;
        private String p_addr;
        private String p_weekt;
        private String p_weekndt;
        private String p_image;

        public PlaceDtoCat(Place place) {
            this.p_id = place.getId();
            this.p_name = place.getP_name();
            this.p_addr = place.getP_addr();
            this.p_weekt = place.getP_weekt();
            this.p_weekndt = place.getP_weekndt();
            this.p_image = place.getP_image();
        }
    }

    @Data
    public static class PlaceDtoCoordinate {
        private long p_id;

        private String p_cate;
        private String p_name;
        private String p_image;

        public PlaceDtoCoordinate(Place place) {
            this.p_id = place.getId();
            this.p_cate = place.getP_cate();
            this.p_name = place.getP_name();
            this.p_image = place.getP_image();
        }
    }

    @Data
    @AllArgsConstructor
    public static class PlaceDto {
        private long p_id;
        private String p_cate;
        private String p_name;

        private String p_weekt;

        private String p_weekndt;

        private String p_addr;

        private String p_image;

        private String p_storeType;

        private float p_latitude;
        private float p_longitude;

        private Integer rent_price;

        //private Point p_gpoint;

        public PlaceDto(Place place) {
            this.p_id = place.getId();
            this.p_cate = place.getP_cate();
            this.p_name = place.getP_name();
            this.p_image = place.getP_image();
            this.p_weekt = place.getP_weekt();
            this.p_weekndt = place.getP_weekndt();
            this.p_addr = place.getP_addr();
            this.p_storeType = place.getP_storeType();
            this.p_latitude = place.getP_latitude();
            this.p_longitude = place.getP_longitude();
            this.rent_price = place.getRent_price();
//            this.p_gpoint = place.getP_gpoint();
        }
    }

    @Data @AllArgsConstructor
    public static class menuDto
    {
        private String m_menu;
        private  int m_price;

        public menuDto(Menu menu) {
            this.m_menu = menu.getM_menu();
            this.m_price = menu.getM_price();
        }
    }
    @Data @AllArgsConstructor
    public static class PlaceDetailDto<T>
    {
        private long p_id;
        private String p_cate;
        private String p_name;
        private String p_weekt;
        private String p_weekndt;
        private String p_addr;
        private String p_image;
        private float p_latitude;
        private float p_longitude;
        private String p_storeType;
        private T menu;
        private Integer rent_price;
        public PlaceDetailDto(Place place, T menu)
        {
            this.p_id = place.getId();
            this.p_cate = place.getP_cate();
            this.p_name = place.getP_name();
            this.p_image = place.getP_image();
            this.p_weekt = place.getP_weekt();
            this.p_weekndt = place.getP_weekndt();
            this.p_addr = place.getP_addr();
            this.p_storeType = place.getP_storeType();
            this.p_latitude =place.getP_latitude();
            this.p_longitude = place.getP_longitude();
            this.rent_price =place.getRent_price();
            //this.p_gpoint = place.getP_gpoint();
        }
    }

    @Data
    @AllArgsConstructor
    public static class placeRecommendDto
    {
        private long p_id;
        private String p_cate;
        private String p_name;
        private String p_image;

        public placeRecommendDto(Feed f){
            this.p_id = f.getPlace().getId();
            this.p_cate = f.getPlace().getP_cate();
            this.p_name = f.getPlace().getP_name();
            this.p_image = f.getPlace().getP_image();
        }



    }
}
