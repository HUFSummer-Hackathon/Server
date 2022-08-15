package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.DownloadDtos.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection.*;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.validation.Valid;
import java.io.IOException;



@RequiredArgsConstructor
@RestController
public class PlaceDownloadApiController
{
    @Data
    public class Example {
        private String title;
        private String description;
        private String language;

        public Example() {}

        // setters and getters go here
    }

    @PostMapping(value="/api/download" , produces = "application/json;charset=UTF-8")
    public ResultResponse userPost(@Valid @RequestBody DownloadRequest request)
    {

        String url =  "https://www.google.co.kr/maps/search/";
        String searchaddr = request.getAddress();
        try {
            Response res = Jsoup.connect(url+request.getAddress()).followRedirects(true).execute();

            Document doc = res.parse();
            Elements metaTags = doc.getElementsByTag("meta");
            String linkOrigin = metaTags.get(10).attr("content");


            int s = linkOrigin.indexOf("=");
            int l = linkOrigin.indexOf("%");
            String longi =linkOrigin.substring(s+1,l);

            s = linkOrigin.indexOf("C");
            l = linkOrigin.indexOf("&");
            String lati =linkOrigin.substring(s+1,l);

            return new ResultResponse("ok",200,longi +":"+lati);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }




}
