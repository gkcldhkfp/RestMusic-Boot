package com.itwill.rest.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.UserSignUpDto;
import com.itwill.rest.service.UserService;

import jakarta.servlet.http.HttpSession;

import com.itwill.rest.service.EmailService;
import com.itwill.rest.service.MailSendService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final UserService userServ;
    
    private final EmailService emailService;
    
    private final MailSendService mailSendService;

    @GetMapping("/signin")
    public void signIn() {
        log.info("GET signIn()");
    }
    
    @GetMapping("/signup")
    public void signUp() {
        log.info("GET signUp()");
    }

    @PostMapping("/signup")
    public String signup(UserSignUpDto dto) {
        log.info("POST signUp(dto = {})", dto);

        // 서비스 계층의 메서드 호출해서 회원가입 정보들을 DB에 저장
        
        User user = userServ.create(dto);
        log.info("user = {}", user);
        
        // 회원가입 성공 시 로그인 페이지로 이동.
        return "redirect:/member/signin";
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
    
    // 사용자 아이디 중복체크 REST 컨트롤러
    @GetMapping("/checkid")
    @ResponseBody // 메서드 리턴 값이 클라이언트로 전달되는 데이터.
    public ResponseEntity<String> checkId(@RequestParam(name = "userid") String userid) {
        log.debug("checkId(userid={})", userid);
        
        boolean result = userServ.checkUserId(userid);
        return ResponseEntity.ok(result ? "Y" : "N");
    }

    // 사용자 이메일 중복체크 REST 컨트롤러
    @GetMapping("/checkemail")
    @ResponseBody // 메서드 리턴 값이 클라이언트로 전달되는 데이터.
    public ResponseEntity<String> checkEmail(@RequestParam(name = "email") String email) {
        log.debug("checkEmail(email={})", email);
        
        boolean result = userServ.checkEmail(email);
        return ResponseEntity.ok(result ? "Y" : "N");
    }
    
    // 사용자 닉네임 중복체크 REST 컨트롤러
    @GetMapping("/checknickname")
    @ResponseBody // 메서드 리턴 값이 클라이언트로 전달되는 데이터.
    public ResponseEntity<String> checkNickname(@RequestParam(name = "nickname") String nickname) {
        log.debug("checkNickname(nickname={})", nickname);
        
        boolean result = userServ.checkNickname(nickname);
        return ResponseEntity.ok(result ? "Y" : "N");
    }
    
    // 이메일 인증 번호 발송
    @GetMapping("/sendEmailAuth")
    @ResponseBody
    public ResponseEntity<String> sendEmailAuth(@RequestParam(name = "email") String email, HttpSession session) {
        log.debug("sendEmailAuth(email={})", email);
        String authNumber = mailSendService.joinEmail(email);
        session.setAttribute("EMAIL_AUTH_NUMBER", authNumber);
        return ResponseEntity.ok(authNumber);
    }

    // 이메일 인증 번호 검증
    @PostMapping("/verifyEmailAuth")
    @ResponseBody
    public ResponseEntity<String> verifyEmailAuth(@RequestParam(name = "inputAuthNumber") String inputAuthNumber, HttpSession session) {
        String authNumber = (String) session.getAttribute("EMAIL_AUTH_NUMBER");
        log.debug("verifyEmailAuth(authNumber={}, inputAuthNumber={})", authNumber, inputAuthNumber);
        if (authNumber != null && authNumber.equals(inputAuthNumber)) {
            return ResponseEntity.ok("Y");
        } else {
            return ResponseEntity.ok("N");
        }
    }
    
}
