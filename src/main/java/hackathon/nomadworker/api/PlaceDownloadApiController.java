package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.DownloadDtos.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection.*;
import org.jsoup.nodes.Element;
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

    @PostMapping(value="/api/download" , produces = "application/json;charset=UTF-8")
    public ResultResponse userPost(@Valid @RequestBody DownloadRequest request)
    {

        String url = "https://www.juso.go.kr/support/AddressMainSearch.do?firstSort=none&ablYn=N&aotYn=N&fillterHiddenValue=&searchKeyword=";
        String searchaddr = request.getAddress();
        try {
            Response res = Jsoup.connect(url+request.getAddress()).followRedirects(true).execute();

            Document doc = res.parse();
            System.out.println(doc);

            return new ResultResponse("ok",200,null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }




}
