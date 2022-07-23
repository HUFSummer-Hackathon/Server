package hackathon.nomadworker.dto;

import lombok.Data;

public class ImageDtos {

    @Data
    public static class PutResponse
    {
        private String message;
        private int status;
        private String u_image;

        public PutResponse(String message, int status, String u_image) {
            this.message = message;
            this.status = status;
            this.u_image = u_image;
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
