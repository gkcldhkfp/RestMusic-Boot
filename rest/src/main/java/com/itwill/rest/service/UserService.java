package com.itwill.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.domain.UserRole;
import com.itwill.rest.dto.UserLikeDto;
import com.itwill.rest.dto.UserSignUpDto;
import com.itwill.rest.repository.LikeRepository;
import com.itwill.rest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final LikeRepository likeRepo;

	@Transactional
	public User create(UserSignUpDto dto) {
		log.info("create(dt = {})", dto);

		User user = userRepo.save(dto.toEntity(passwordEncoder).addRole(UserRole.USER));
				// save() -> (1) insert into members, (2) insert into member_roles
		//?총 2개의 테이블에 insert되는 메서드이다.
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
				// DB 테이블(members)에 username이 일치하는 사용자가 있으면 UserDatails 타입의 객체를 리턴하고
		// 그렇지 않으면 UsernameNotFoundException을 던짐(throws)
		log.info("loadUserByUsername(userId={})", userId);
		Optional<User> entity = userRepo.findByUserId(userId);
		if(entity.isPresent()) {
			return entity.get(); // Entity를 Member 타입으로 변환 후 리턴
			// Member는 UserDatails를 구현하고 있으므로 다형성으로 리턴하는거임.
			// ?리턴타입 UserDetails
		} else {
			throw new UsernameNotFoundException(userId + ": 일치하는 사용자 정보 없음.");
		}
	}

	@Transactional
	public boolean checkUserId(String userid) {
		Optional<User> user = userRepo.findByUserId(userid);
		if (!user.isPresent()) {
			// Optional객체의 존재하는 테이블인 지 검사하는 코드
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean checkEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean checkNickname(String nickname) {
		User user = userRepo.findByNickname(nickname);
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}
	
	// 마이페이지에서 유저 정보를 유저 id로 찾아오는 메서드
	@Transactional(readOnly = true)
	public User readById(Integer id) {
		log.info("readById={}", id);
		
		User user = userRepo.findById(id).orElseThrow();
		
		return user;
	}
	
	@Transactional(readOnly = true)
	public List<UserLikeDto> selectLikesByUserid(Integer id) {
		
		List<UserLikeDto> list = userRepo.selectLikesByUserid(id);
		
		return list;
	}
	
	@Transactional(readOnly = true)
	public List<Song> getLikeSongByUserId(Integer id) {
		// 아이디로 음원 찾기
		List<Like> likes =likeRepo.findByLikeId_id(id);
		List<Song> songs = likes.stream().map(l -> l.getSong()).collect(Collectors.toList());

		return songs;
	}
	
}
