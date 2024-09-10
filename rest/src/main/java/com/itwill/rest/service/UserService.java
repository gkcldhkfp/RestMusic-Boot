package com.itwill.rest.service;

import java.util.Comparator;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Artist;
import com.itwill.rest.domain.Group;
import com.itwill.rest.domain.Like;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.domain.UserRole;
import com.itwill.rest.dto.UserLikeDto;
import com.itwill.rest.dto.UserSignUpDto;
import com.itwill.rest.repository.GroupMemberRepository;
import com.itwill.rest.repository.LikeRepository;
import com.itwill.rest.repository.SongRepository;
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
	private final LikeRepository likeRepo;
	private final SongRepository songRepo;
	private final GroupMemberRepository groupMemberRepo;

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
	public List<UserLikeDto> getLikeSongByUserId(Integer id) {
	    // 1. 사용자 ID로 Like 엔티티 조회
	    List<Like> likes = likeRepo.findByLikeId_id(id);
	    
	    // 2. Like 엔티티에서 Song 객체를 추출
	    List<Song> songs = likes.stream()
	            .map(like -> {
	                // LikeId에서 songId를 통해 Song 엔티티를 조회
	                Integer songId = like.getLikeId().getSongId();
	                return songRepo.findById(songId).orElse(null);
	            })
	            .filter(song -> song != null) // Null 값 필터링
	            .collect(Collectors.toList());
	    
	    // 3. Song 객체를 title 기준으로 정렬
	    List<Song> sortedSongs = songs.stream()
	            .sorted(Comparator.comparing(Song::getTitle))
	            .collect(Collectors.toList());

	    // 4. Song 객체를 UserLikeDto로 변환
	    return sortedSongs.stream()
	            .map(song -> {
	                // 3.1. Song 객체에서 Album 정보 추출
	                Album album = song.getAlbum();
	                
	                // 3.2. Song 객체에서 Artist 및 Group 정보 추출
	                List<Artist> songArtists = song.getArtistRole().stream()
	                        .filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                        .map(artistRole -> artistRole.getArtist())
	                        .distinct()
	                        .collect(Collectors.toList());

	                List<Group> songGroups = song.getArtistRole().stream()
	                        .filter(artistRole -> artistRole.getRoleCode().getRoleId() == 10)
	                        .filter(artistRole -> artistRole.getGroup() != null)
	                        .map(artistRole -> artistRole.getGroup())
	                        .distinct()
	                        .collect(Collectors.toList());
	                
	                // 그룹에 속한 아티스트 ID 목록
	                List<Integer> groupArtistIds = songGroups.stream()
	                        .flatMap(group -> groupMemberRepo.findByGroupId(group.getId()).stream())
	                        .map(groupMember -> groupMember.getArtist().getId())
	                        .collect(Collectors.toList());

	                // 3.3. Artist와 Group ID 및 이름 리스트 생성
	                List<Integer> artistIds = songArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getId)
	                        .collect(Collectors.toList());

	                List<String> artistNames = songArtists.stream()
	                        .filter(artist -> !groupArtistIds.contains(artist.getId()))
	                        .map(Artist::getArtistName)
	                        .collect(Collectors.toList());

	                List<Integer> groupIds = songGroups.stream()
	                        .map(Group::getId)
	                        .collect(Collectors.toList());

	                List<String> groupNames = songGroups.stream()
	                        .map(Group::getGroupName)
	                        .collect(Collectors.toList());

	                // 3.4. UserLikeDto 객체 생성 및 반환
	                return UserLikeDto.builder()
	                        .songId(song.getSongId())
	                        .title(song.getTitle())
	                        .albumId(album.getAlbumId())
	                        .albumName(album.getAlbumName())
	                        .albumImage(album.getAlbumImage())
	                        .artistId(artistIds)
	                        .artistName(artistNames)
	                        .groupId(groupIds)
	                        .groupName(groupNames)
	                        .build();
	            })
	            .collect(Collectors.toList());
	}
	
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
        user.updateProfile(null); // Clear profile
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
        if (user == null || !checkPassword(id, password)) {
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
    
    // 비밀번호 일치 확인(정보수정 페이지에서 사용)
    public boolean checkPassword(Integer id, String checkPassword) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        String realPassword = user.getPassword();
        boolean matches = passwordEncoder.matches(checkPassword, realPassword);
        return matches;
    }
    
}
