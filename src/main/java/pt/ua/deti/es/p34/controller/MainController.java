package pt.ua.deti.es.p34.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(value={"/"})
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping(value={"/statistics"})
    public String getStatistics() {
        return "statistics.html";
    }
    /** 
    @RequestMapping(value={"/getStatisticsData"})
    public String getStatistics1() {
        
        //fazer query e envar dados..
        //JSON....
        return "statistics.html";
    }
    */
}

