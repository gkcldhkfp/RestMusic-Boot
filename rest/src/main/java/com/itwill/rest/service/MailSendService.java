package com.itwill.rest.service;

import java.util.Random;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailSendService {

    @Autowired
    private JavaMailSender mailSender; // JavaMailSender 인터페이스 사용

    // 난수 생성 메서드
    public String makeRandomNumber() {
        Random r = new Random();
        int checkNum = r.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkNum);
        return Integer.toString(checkNum);
    }

    // 이메일을 보내기 위한 메서드
    public String joinEmail(String email) {
        String authNumber = makeRandomNumber(); // 인증번호 생성
        String setFrom = "ourrestmusic@gmail.com"; // 발신자 이메일
        String toMail = email; // 수신자 이메일
        String title = "rest 회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content = "rest를 방문해주셔서 감사합니다.<br><br>"
        		+ "🎵 our only healing music, rest 🎵 <br><br>" +
                "인증 번호는 " + authNumber + "입니다.<br><br>" +
                "해당 인증번호를 인증번호 확인란에 입력하세요."; // 이메일 내용
        mailSend(setFrom, toMail, title, content); // 이메일 전송
        return authNumber; // 인증번호 반환
    }

    // 실제 이메일 전송을 수행하는 메서드
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage(); // MimeMessage 객체 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8"); // MimeMessageHelper를 통해 메시지 설정
            helper.setFrom(setFrom); // 발신자 설정
            helper.setTo(toMail); // 수신자 설정
            helper.setSubject(title); // 제목 설정
            helper.setText(content, true); // 내용 설정 (true: HTML 형식)
            mailSender.send(message); // 이메일 전송
        } catch (MessagingException e) {
            e.printStackTrace(); // 전송 중 예외 발생 시 출력
        }
    }
}
