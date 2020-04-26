package pt.ua.deti.es.p34.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {
    @RequestMapping(value={"/"})
    public String getIndex() {
        return "index.html";
    }
}
