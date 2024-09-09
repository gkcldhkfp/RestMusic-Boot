package com.itwill.rest.service;

import com.itwill.rest.domain.User;
import com.itwill.rest.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 이름과 이메일로 아이디 찾기
    public String findUserId(String userName, String email) {
        Optional<User> user = memberRepository.findByUserNameAndEmail(userName, email);
        return user.map(User::getUserId).orElse(null);
    }

    // 사용자 비밀번호 찾기 정보 검증
    public boolean validateUserPasswordInfo(String userId, String email, String hintQuestionSelect, String hintAnswer) {
        Optional<User> user = memberRepository.findByUserIdAndEmailAndHintQuestionAndHintAnswer(userId, email, hintQuestionSelect, hintAnswer);
        return user.isPresent();
    }

    // 비밀번호 업데이트
    public boolean updatePassword(String userId, String newPassword) {
        Optional<User> userOptional = memberRepository.findByUserId(userId); // userId로 조회
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updatePassword(passwordEncoder.encode(newPassword));
            memberRepository.save(user);
            return true;
        }
        return false;
    }
}
