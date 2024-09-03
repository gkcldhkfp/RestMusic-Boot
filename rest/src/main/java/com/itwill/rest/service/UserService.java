package com.itwill.rest.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwill.rest.domain.User;
import com.itwill.rest.domain.UserRole;
import com.itwill.rest.dto.UserSignUpDto;
import com.itwill.rest.dto.UserUpdateDto;
import com.itwill.rest.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

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
    public User readInfo(String userId) {
        return userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
    }
	
	@Transactional
	public boolean updateProfileImage(String userId, MultipartFile profileImage, HttpServletRequest request) {
	    try {
	        User user = userRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

	        // 이미지 파일인지 확인
	        if (!profileImage.getContentType().startsWith("image/")) {
	            throw new RuntimeException("Uploaded file is not an image");
	        }

	        // 파일 크기 제한
	        final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
	        if (profileImage.getSize() > MAX_FILE_SIZE) {
	            throw new RuntimeException("Uploaded file is too large");
	        }

	        // 원본 파일명 사용
	        String originalFilename = profileImage.getOriginalFilename();
	        if (originalFilename == null || originalFilename.isEmpty()) {
	            throw new RuntimeException("Uploaded file has no name");
	        }

	        // 업로드 디렉토리 설정
	        String uploadDir = request.getServletContext().getRealPath("/images/profileimage");
	        System.out.println("Upload directory: " + uploadDir);
	        File uploadDirFile = new File(uploadDir);
	        if (!uploadDirFile.exists()) {
	            uploadDirFile.mkdirs();
	        }

	        // 파일 경로 설정
	        String filePath = uploadDir + File.separator + originalFilename;
	        File file = new File(filePath);
	        
	        // 파일명 중복 처리
	        int count = 1;
	        while (file.exists()) {
	            String nameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
	            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	            originalFilename = nameWithoutExtension + "(" + count + ")" + extension;
	            filePath = uploadDir + File.separator + originalFilename;
	            file = new File(filePath);
	            count++;
	        }
	        
	        if (profileImage.isEmpty()) {
	            throw new RuntimeException("Uploaded file is empty");
	        }

	        // 파일 저장
	        profileImage.transferTo(file);

	        // 파일명 데이터베이스에 저장
	        String webPath = originalFilename;
	        user.updateProfile(webPath);
	        userRepo.save(user);

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    @Transactional
    public boolean deleteUserProfile(String userId) {
        User user = userRepo.findByUserId(userId).orElse(null);
        if (user == null) {
            return false;
        }
        user.updateProfile(""); // Clear profile
        userRepo.save(user);
        return true;
    }

    @Transactional
    public boolean update(UserUpdateDto dto) {
        log.debug("update(dto = {})", dto);

        if (dto.getUserProfile() == null) {
            dto.setUserProfile("");
        }
        if (dto.getHintQuestion() == null) {
            dto.setHintQuestion("");
        }
        if (dto.getHintAnswer() == null) {
            dto.setHintAnswer("");
        }

        User user = userRepo.findByUserId(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.updateUser(dto.getPassword(), dto.getEmail(), dto.getNickname(), dto.getUserProfile(),
                        dto.getHintQuestion(), dto.getHintAnswer());
        userRepo.save(user);
        return true;
    }

    @Transactional
    public boolean deactivateAccount(Integer id, String password) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null || !userRepo.checkPassword(id, password)) {
            return false;
        }

        user.deactivateUser(LocalDate.now().plusDays(30)); // Example deactivation period
        userRepo.save(user);
        return true;
    }

    @Transactional
    public boolean checkUserIsActive(String userId) {
        return userRepo.checkUserIsActive(userId);
    }

    @Transactional
    public boolean checkDeactivationPeriod(String userId) {
        return userRepo.checkDeactivationPeriod(userId);
    }

    @Transactional
    public User getUserById(Integer id) {
        return userRepo.findById(id).orElse(null);
    }
	
}
