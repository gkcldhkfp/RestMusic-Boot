package com.itwill.rest.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itwill.rest.domain.User;
import com.itwill.rest.dto.UserDeactivateDto;
import com.itwill.rest.dto.UserLikeDto;
import com.itwill.rest.dto.UserSignUpDto;
import com.itwill.rest.dto.UserUpdateDto;
import com.itwill.rest.service.EmailService;
import com.itwill.rest.service.MailSendService;
import com.itwill.rest.service.MemberService;
import com.itwill.rest.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

	private final MemberService memberServ;

	private final BCryptPasswordEncoder encoder;

	@GetMapping("/signin")
	public void signIn(@RequestParam(name = "targetUrl", required = false) String targetUrl, HttpServletRequest request,
			Model model, Authentication authentication) {
		log.info("GET signIn()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		if (targetUrl != null && !targetUrl.isEmpty()) {
			SavedRequest savedRequest = new DefaultSavedRequest.Builder().setScheme(request.getScheme())
					.setServerName(request.getServerName()).setServerPort(request.getServerPort())
					.setContextPath(request.getContextPath()).setRequestURI(targetUrl).build();
			request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
		}
	}

	@GetMapping("/signup")
	public void signUp(Model model, Authentication authentication) {
		log.info("GET signUp()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
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
	public void findUserId(Model model, Authentication authentication) {
		log.info("GET findUserId()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
	}

	// 아이디 찾기 결과 페이지
	@PostMapping("/finduserresult")
	public String findUserResult(@RequestParam("userName") String userName,
			@RequestParam("email") String email,
			Model model, Authentication authentication) {
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
		String userId = memberServ.findUserId(userName, email);

		if (userId != null) {
			model.addAttribute("userId", userId);
			return "member/finduserresult"; // 결과 페이지로 이동
		} else {
			model.addAttribute("result", "f");
			return "member/finduserid"; // 다시 아이디 찾기 페이지로 이동
		}
	}

	@PostMapping("/setuserpassword")
	public String setUserPassword(@RequestParam("userId") String userId,
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			Model model) {
		log.info("POST setUserPassword()");
		// 비밀번호와 비밀번호 확인 일치 여부 확인
		if (!password.equals(confirmPassword)) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			return "member/setuserpassword"; // 실패 시 다시 변경 페이지로
		}

		String rawPassword = encoder.encode(password);
		// 비밀번호 업데이트 로직 호출
		boolean isUpdated = memberServ.updatePassword(userId, rawPassword);

		if (isUpdated) {
			model.addAttribute("", "비밀번호가 성공적으로 변경되었습니다.");
			return "redirect:/member/signin"; // 성공 시 로그인 페이지로 리다이렉트
		} else {
			model.addAttribute("errorMessage", "비밀번호 변경에 실패했습니다.");
			return "member/setuserpassword"; // 실패 시 다시 변경 페이지로
		}
	}

	@GetMapping("/finduserresult")
	public void findUserResult(@RequestParam(name = "userId") String userId, Model model, Authentication authentication) {
		model.addAttribute("userId", userId);
		log.info("GET findUserResult()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
	}

	// 비밀번호 찾기 페이지
	@GetMapping("/finduserpassword")
	public void findUserPassword(Model model, Authentication authentication) {
		log.info("GET findUserPassword()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
	}

	// 비밀번호 변경 페이지
	@GetMapping("/setuserpassword")
	public void setUserPassword(Model model, Authentication authentication) {
		log.info("GET setUserPassword()");
		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);
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

	@PostMapping("/emailConfirm")
	public String emailConfirm(@RequestParam(name = "email") String email) throws Exception {
		String confirm = emailService.sendSimpleMessage(email);
		return confirm;
	}

	// 사용자 닉네임 중복체크 REST 컨트롤러
	@GetMapping("/checknickname")
	@ResponseBody // 메서드 리턴 값이 클라이언트로 전달되는 데이터.
	public ResponseEntity<String> checkNickname(@RequestParam(name = "nickname") String nickname) {
		log.debug("checkNickname(nickname={})", nickname);

		boolean result = userServ.checkNickname(nickname);
		return ResponseEntity.ok(result ? "Y" : "N");
	}

	@GetMapping("/update")
	public void myPage(@RequestParam(name = "userId") String userId, Model model) {
		log.debug("userId={}", userId);
		User user = userServ.readInfo(userId); // 유저 정보 불러오기(프로필 사진, 닉네임 출력)
		model.addAttribute("user", user);
	}

	// 프로필 이미지 변경
	@PostMapping("/updateProfileImage")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateProfileImage(@RequestParam("userId") String userId,
			@RequestParam("profileImage") MultipartFile profileImage, HttpServletRequest request) {
		boolean isUpdated = userServ.updateProfileImage(userId, profileImage, request);
		Map<String, Object> response = new HashMap<>();

		if (isUpdated) {
			String imageUrl = "/images/profileimage/" + profileImage.getOriginalFilename();
			response.put("success", true);
			response.put("message", "Profile image updated successfully");
			response.put("imageUrl", imageUrl);
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Failed to update profile image");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 프로필 이미지 삭제
	@DeleteMapping("/deleteProfileImage/{userId}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteProfileImage(@PathVariable(name = "userId") String userId) {
		Map<String, Object> response = new HashMap<>();
		boolean isDeleted = userServ.deleteUserProfile(userId);

		if (isDeleted) {
			response.put("success", true);
			response.put("message", "Profile image deleted successfully");
			return ResponseEntity.ok(response);
		} else {
			response.put("success", false);
			response.put("message", "Failed to delete profile image");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// 사용자 정보 수정
	@PostMapping("/update")
	public String update(UserUpdateDto dto,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {
		log.debug("POST: update(dto = {}, file = {})", dto, profileImage);

		if (profileImage != null && !profileImage.isEmpty()) {
			String filePath = profileImage.getOriginalFilename();
			File destinationFile = new File(filePath);
			profileImage.transferTo(destinationFile);
			dto.setUserProfile(filePath);
		}

		userServ.update(dto);

		return "redirect:/member/mypage?id=" + dto.getId();
	}

	// 사용자 계정 비활성화 페이지
	@GetMapping("/deactivateUser")
	public String deactivateAccount(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/member/signin";
		}

		User user = (User) authentication.getPrincipal();
		Integer loginUserId = user.getId();
		User userDetails = userServ.getUserById(loginUserId);

		model.addAttribute("user", userDetails);
		model.addAttribute("loginUserId", loginUserId);

		return "member/deactivateUser";
	}

    // 사용자 계정 비활성화
    @PostMapping("/deactivateUser")
    @ResponseBody
    public ResponseEntity<?> deactivateAccount(@RequestBody UserDeactivateDto dto, Authentication authentication,
                    HttpServletResponse response, HttpServletRequest request) {
            if (authentication == null || !authentication.isAuthenticated()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
            }
            log.info("deactivateAccount(dto={})", dto);

            User user = (User) authentication.getPrincipal();
            Integer id = user.getId();
            String password = dto.getPassword();

            boolean result = userServ.deactivateAccount(id, password);
            log.info("deactivateComplete={}", result);

            if (result) {
                    // 세션 무효화
                    SecurityContextHolder.clearContext();
                    
            // 세션 무효화
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

                    // 쿠키 삭제
                    Cookie cookie = new Cookie("user", null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    return ResponseEntity.ok().body("계정이 탈퇴되었습니다.");
            } else {
                    return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
            }
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
	public ResponseEntity<String> verifyEmailAuth(@RequestParam(name = "inputAuthNumber") String inputAuthNumber,
			HttpSession session) {
		String authNumber = (String) session.getAttribute("EMAIL_AUTH_NUMBER");
		log.debug("verifyEmailAuth(authNumber={}, inputAuthNumber={})", authNumber, inputAuthNumber);
		if (authNumber != null && authNumber.equals(inputAuthNumber)) {
			return ResponseEntity.ok("Y");
		} else {
			return ResponseEntity.ok("N");
		}
	}

	@PreAuthorize("hasRole('USER')") // -> 로그인한(USER Role을 가진) 유저만 접속할 수 있게 제한
	@GetMapping("/mypage")
	public void myPage(@RequestParam(name = "id") Integer id, Model model) {
		log.info("myPage(id={})", id);

		User user = userServ.readById(id);

		model.addAttribute("user", user);
	}

	@GetMapping("/getUserLike/{id}")
	@ResponseBody
	public ResponseEntity<List<UserLikeDto>> getUserLike(@PathVariable(name = "id") Integer id, Model model) {
		log.info("getUserLike={}", id);

		// UserService를 통해 해당 사용자 ID로 좋아요 리스트 조회
		List<UserLikeDto> list = userServ.getLikeSongByUserId(id);

		log.info("list = {}", list);
		return ResponseEntity.ok(list);
	}

	// 비밀번호 검증(정보수정 페이지 진입전 사용)
	@GetMapping("/checkPwd")
	@ResponseBody
	public ResponseEntity<Boolean> checkPassword(@AuthenticationPrincipal User user,
			@RequestParam(name = "checkPassword") String checkPassword, Model model) {
		log.info("checkPassword(user={}, checkPassword={})", user, checkPassword);
		Integer id = user.getId();

		boolean result = userServ.checkPassword(id, checkPassword);

		return ResponseEntity.ok(result);
	}

}
