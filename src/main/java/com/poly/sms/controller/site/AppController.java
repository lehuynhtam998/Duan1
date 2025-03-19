package com.poly.sms.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("sms")
public class AppController {

    @RequestMapping("app")
    public String app(Model model) {
        return "app/index";
    }

}
