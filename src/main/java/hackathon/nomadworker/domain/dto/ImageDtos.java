package hackathon.nomadworker.domain.dto;

import lombok.Data;

public class ImageDtos {

    @Data
    public static class PutResponse
    {
        private String message;
        private int status;


        public PutResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }

    @Data
    public static class DeleteResponse
    {
        private String message;
        private int status;

        public DeleteResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}
