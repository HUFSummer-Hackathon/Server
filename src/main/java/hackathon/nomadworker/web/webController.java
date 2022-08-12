package hackathon.nomadworker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class webController {

    @RequestMapping(value = "/home")
    public String home(){
        return "index.html";
    }
}
