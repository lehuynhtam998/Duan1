package com.poly.sms.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.sms.entity.Email;
import com.poly.sms.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("sms")
public class ContactController {

    // @RequestMapping("contact")
    // public String requestMethodName() {
    // return "site/contact";
    // }

    @Autowired
    private EmailService emailService;

    @GetMapping("contact")
    public String showForm(Model model, HttpSession session) {
        // Tạo một đối tượng Email mới và thêm vào model
        model.addAttribute("email", new Email());

        // Đặt giá trị message trong session là false
        session.setAttribute("message", false);

        return "site/contact";
    }

    @PostMapping("send")
    public String sendEmail(@ModelAttribute Email email, BindingResult result, Model model,
            @RequestParam(value = "message", defaultValue = "false") boolean message, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Vui lòng kiểm tra các lỗi nhập liệu.");
            return "site/contact";
        }
        try {
            emailService.sendEmail(email);
            session.setAttribute("message", message);
            emailService.saveEmail(email);
            model.addAttribute("successMessage", "Email đã được gửi thành công!");
            model.addAttribute("email", new Email());
        } catch (Exception e) {
            model.addAttribute("successMessage", "Lỗi khi gửi email: " + e.getMessage());
        }
        return "site/contact";
    }
}