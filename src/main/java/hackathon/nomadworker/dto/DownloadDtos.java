package hackathon.nomadworker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

public class DownloadDtos {

    @Data
    @AllArgsConstructor
    public static class ResultResponse<T> {
        private String message;
        private int status;
        private T data;
    }

    @Data
    public static class DownloadRequest
    {
        private String address;
    }

    @Data
    public static class DownloadResponse
    {
        private float lati;
        private float longi;

        public DownloadResponse(float lati, float longi) {
            this.lati = lati;
            this.longi = longi;
        }
    }




}
