package hackathon.nomadworker.dto;

import lombok.Data;

public class ImageDtos {

    @Data
    public static class PostResponse
    {
        private String message;
        private int status;
        private String u_image;

        public PostResponse(String message, int status, String u_image) {
            this.message = message;
            this.status = status;
            this.u_image = u_image;
        }
    }
}
