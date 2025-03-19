package com.poly.sms.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.sms.config.AccountDetailService;

@Controller
public class AuthController {

    @Autowired
    private AccountDetailService accountDetailService;

    @RequestMapping("sms/login")
    public String form() {
        return "site/login";
    }

    @RequestMapping("login/error")
    public String error(Model model) {
        model.addAttribute("message", "Sai thông tin đăng nhập");
        return "forward:/sms/login";
    }

    @RequestMapping("logout/success")
    public String logout(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "forward:/sms/login";
    }

    @RequestMapping("access/denied")
    public String access(Model model) {
        model.addAttribute("message", "Bạn không có quyền truy xuất");
        return "forward:/sms/login";
    }

    @RequestMapping("oauth2/login/success")
    public String oauth2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oau = (OAuth2AuthenticationToken) authentication;
            accountDetailService.loginFromOAuth2(oau);
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuth = (UsernamePasswordAuthenticationToken) authentication;
            accountDetailService.loadUserByUsername(usernamePasswordAuth.getName());
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            // Người dùng chưa đăng nhập
            return "redirect:/sms/login"; // Hoặc bất kỳ trang nào bạn muốn chuyển hướng khi người dùng chưa đăng nhập
        } else {
            throw new IllegalStateException("Unexpected authentication type: " + authentication);
        }
        return "forward:/sms/home";
    }

}
