package com.itwill.rest.repository;

import com.itwill.rest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<User, Integer> {

    // 사용자 이름과 이메일로 아이디 찾기
    Optional<User> findByUserNameAndEmail(String userName, String email);
    
    // 사용자 이메일과 비밀번호 힌트로 비밀번호 찾기
    Optional<User> findByUserIdAndEmailAndHintQuestionAndHintAnswer(String userId, String email, String hintQuestion, String hintAnswer);

    // 사용자 아이디로 조회
    Optional<User> findByUserId(String userId);
}
