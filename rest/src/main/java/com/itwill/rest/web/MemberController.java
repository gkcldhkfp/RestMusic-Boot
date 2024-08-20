package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    
    private final EmailService emailService;

    @GetMapping("/signin")
    public void signIn() {
        log.info("GET signIn()");
    }
    
    @GetMapping("/signup")
    public void signUp() {
        log.info("GET signUp()");
    }

    // 아이디 찾기 페이지
    @GetMapping("/finduserid")
    public void findUserId() {
        log.info("GET findUserId()");
    }

    // 아이디 찾기 결과 페이지
    @GetMapping("/finduserresult")
    public void findUserResult(@RequestParam(name = "userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        log.info("GET findUserResult()");
    }

    // 비밀번호 찾기 페이지
    @GetMapping("/finduserpassword")
    public void findUserPassword() {
        log.info("GET findUserPassword()");
    }

    // 비밀번호 변경 페이지
    @GetMapping("/setuserpassword")
    public void setUserPassword() {
        log.info("GET setUserPassword()");
    }
    
    @PostMapping("/emailConfirm")
    public String emailConfirm(@RequestParam String email) throws Exception {
        String confirm = emailService.sendSimpleMessage(email);
        return confirm;
    }
}
