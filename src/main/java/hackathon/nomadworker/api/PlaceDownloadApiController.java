package hackathon.nomadworker.api;
import hackathon.nomadworker.dto.DownloadDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.validation.Valid;

import static org.apache.http.client.methods.RequestBuilder.post;

@RequiredArgsConstructor
@RestController
public class PlaceDownloadApiController
{

    @PostMapping(value="/api/download" , produces = "application/json;charset=UTF-8")
    public ResultResponse userPost(@Valid @RequestBody DownloadRequest request)
    {

        String url =  "https://address.dawul.co.kr";
        String searchaddr = request.getAddress();
        String ele ="input[name=input_juso]";

        DownloadResponse data = new DownloadResponse((float)0.0,(float)0.0);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).data(ele,searchaddr).userAgent("Mozilla").post();
        } catch (Exception e) {
            System.out.println(e);
        }


//        <input id="input_juso" type="text" name="juso" class="juso" value="전환하실 주소를 입력하세요"
//        onfocus="clearText2()" size="50" style="height:27px; border:3px solid #478ec2; width:365px;font-family:
//        Malgun Gothic, Dotum, Gulim, Verdana, Arial, Sans-Serif; font-size:10pt; color:#333333; line-height:25px;">
        //('input[name=input_juso]').attr('value',searchaddr);

        System.out.println(doc);
        Element elem = doc.getElementById("insert_data_5");

        String[] str = elem.text().split(" ");



         return new ResultResponse("ok",200,str);
    }




}
