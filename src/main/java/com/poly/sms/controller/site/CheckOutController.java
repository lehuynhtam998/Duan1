package com.poly.sms.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("sms")
public class CheckOutController {

    @GetMapping("checkout")
    public String get() {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // String username = authentication.getName();
        // Employee employee = employeeService.findByUsername(username);

        // model.addAttribute("user", employee);
        return "site/checkout";
    }

}
