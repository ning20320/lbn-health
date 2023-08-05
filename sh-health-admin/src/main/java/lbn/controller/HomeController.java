package lbn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String gotoIndex() {
        return "main";
    }

    @GetMapping("/checkitem")
    public String gotoCheckItem(){
        return "checkitem";
    }

    @GetMapping("/checkgroup")
    public String gotoCheckGroup(){
        return "checkgroup";
    }

    @GetMapping("/setmeal")
    public String gotoSetmeal(){
        return "setmeal";
    }

    @GetMapping("/ordersetting")
    public String gotoOrderSetting(){
        return "ordersetting";
    }

    @GetMapping("/reportmember.html")
    public String gotoReport(){
        return "reportmember";
    }

}