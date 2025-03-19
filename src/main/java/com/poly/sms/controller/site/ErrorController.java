package com.poly.sms.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sms")
public class ErrorController {
    @RequestMapping("404")
    public String e404() {
        return "site/404";
    }

}
