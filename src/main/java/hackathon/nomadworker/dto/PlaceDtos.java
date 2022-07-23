package hackathon.nomadworker.dto;

import hackathon.nomadworker.domain.Feed;
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

        private String rent_price;

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

        private String p_url;
        public PlaceDetailDto(Place place)
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
            this.p_url =place.getRent_price();


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

    @Data
    public static class placecathomeresponse
    {
        private String thumbnail_image_url;

        private String location;

        public placecathomeresponse(String url ,String location)
        {
            this.thumbnail_image_url = url;
            this.location = location;

        }

    }

    @Data
    @AllArgsConstructor
    public static class placeSearchDto {
        private long p_id;
        private String place_name;
        private String place_address;
        private String place_weektime;
        private String place_weekendtime;
        private String p_image;

        public placeSearchDto(Place p) {
            this.p_id = p.getId();
            this.place_name = p.getP_name();
            this.place_address = p.getP_addr();
            this.place_weektime = p.getP_weekt();
            this.place_weekendtime = p.getP_weekndt();
            this.p_image = p.getP_image();

        }
    }

    @Data
    @AllArgsConstructor
    public static class placeSearchOneByNameDto {
        private long p_id;
        private String place_name;

        public placeSearchOneByNameDto(Place p) {
            this.p_id = p.getId();
            this.place_name = p.getP_name();

        }
    }

}
