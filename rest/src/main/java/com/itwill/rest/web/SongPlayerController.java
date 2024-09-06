package com.itwill.rest.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.SongPlayerDto;
import com.itwill.rest.service.AlbumSongsService;
import com.itwill.rest.service.SongService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SongPlayerController {

	private final AlbumSongsService albumServ;
	private final SongService songServ;

	private final ObjectMapper objectMapper;

	@GetMapping("/player/playerPage")
	@Transactional
	public void playerPage(
			Model model,
			HttpSession session, Authentication authentication) throws JsonProcessingException {

		Integer loginUserId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            loginUserId = user.getId();
        }
        model.addAttribute("loginUserId", loginUserId);
		// 세션에서 JSON 문자열을 가져옴
		String cPListJson = (String) session.getAttribute("cPListJson");

		log.info("cPListJson = {}", cPListJson);

		// 모델에 JSON 문자열 추가 (선택 사항, 필요에 따라 조정)
		model.addAttribute("cPListJson", cPListJson);
		session.setAttribute("cPListJson", cPListJson);
	}

	@GetMapping("/song/addCurrentPlayList")
	@ResponseBody
	@Transactional
	public ResponseEntity<SongPlayerDto> addCurrentPlayList(
			@RequestParam(name = "songId") Integer songId,
			HttpSession session) throws JsonProcessingException {

		// 세션에서 JSON 문자열을 가져옴
		String cPListJson = (String) session.getAttribute("cPListJson");

		List<SongPlayerDto> cPList = new ArrayList<>();
		if (cPListJson != null && !cPListJson.trim().isEmpty()) {
			try {
				// JSON 문자열을 List<SongPlayerDto>로 변환
				cPList = objectMapper.readValue(cPListJson,
						objectMapper.getTypeFactory().constructCollectionType(List.class, SongPlayerDto.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("songId = {}", songId);
		Song song = songServ.selectBySongId(songId);
		Album album = song.getAlbum();
		// 요청 파라미터로 받은 songId로 음악 객체를 생성함.
		log.debug("song = {}", song);
		log.debug("album = {}", album);

		SongPlayerDto dto = SongPlayerDto.fromEntity(album, song, albumServ);
		cPList.add(dto);
		// 생성한 음악 객체를 현재 재생 목록에 추가함.

		// jackson objectmapper 객체 생성
		cPListJson = objectMapper.writeValueAsString(cPList);
		// List -> Json 문자열
		// Json 문자열 출력

		log.debug("cPList = {}", cPListJson);
		session.setAttribute("cPListJson", cPListJson);
		// 재생목록을 세션에 업데이트.

		return ResponseEntity.ok(dto);
	}

	@GetMapping("/song/listen")
	@ResponseBody
	@Transactional
	public ResponseEntity<SongPlayerDto> listen(
			@RequestParam(value = "songId") Integer songId,
			HttpSession session) throws JsonProcessingException {

		session.setAttribute("cPListJson", null);
		// 바로듣기 버튼 클릭 시 세션에 저장된 리스트를 지움.
		List<SongPlayerDto> cPList = new ArrayList<>();
		// 리스트를 지웠으므로 새 리스트를 생성해줌.
		Song song = songServ.selectBySongId(songId);
		// 요청 파라미터로 받은 songId로 음악 객체를 생성
		Album album = song.getAlbum();
		// 요청 파라미터로 받은 songId로 음악 객체를 생성함.
		log.debug("song = {}", song);
		log.debug("album = {}", album);

		SongPlayerDto dto = SongPlayerDto.fromEntity(album, song, albumServ);
		// 새로 생성한 리스트에 음악 객체를 추가.
		cPList.add(dto);
		// jackson objectmapper 객체 생성
		String cPListJson = objectMapper.writeValueAsString(cPList);
		// List -> Json 문자열
		System.out.println(cPListJson);
		// Json 문자열 출력
		session.setAttribute("cPListJson", cPListJson);
		// 세션에 리스트를 업데이트

		return ResponseEntity.ok(dto);
	}

	// 요청받은 songId가 세션에 있는 지 검사하는 매핑 컨트롤러
	@GetMapping("/song/getCPList")
	@Transactional
	public ResponseEntity<Boolean> getCPList(@RequestParam(value = "songId") Integer songId, HttpSession session) {

		// 세션에서 현재 재생목록을 가져옴.
		// 세션에서 JSON 문자열을 가져옴
		String cPListJson = (String) session.getAttribute("cPListJson");

		List<SongPlayerDto> cPList = new ArrayList<>();
		if (cPListJson != null && !cPListJson.trim().isEmpty()) {
			try {
				// JSON 문자열을 List<SongPlayerDto>로 변환
				cPList = objectMapper.readValue(cPListJson,
						objectMapper.getTypeFactory().constructCollectionType(List.class, SongPlayerDto.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (cPListJson == null) {
			// cPList null인경우 그냥 false(중복된 데이터 없음.)을 리턴.
			return ResponseEntity.ok(false);
		}
		// 요청받은 객체가 현재 세션에 있는 지 검사
		Song song = songServ.selectBySongId(songId);
		Album album = song.getAlbum();
		SongPlayerDto dto = SongPlayerDto.fromEntity(album, song, albumServ);

		boolean containsAlbumSong = cPList.contains(dto);

		return ResponseEntity.ok(containsAlbumSong);

	}

	// 재생목록 비우는 매핑컨트롤러
	@GetMapping("/song/empty")
	@ResponseBody
	@Transactional
	public ResponseEntity<List<SongPlayerDto>> empty(HttpSession session) throws JsonProcessingException {
		session.setAttribute("cPList", null);
		// 바로듣기 버튼 클릭 시 세션에 저장된 리스트를 지움.
		List<SongPlayerDto> cPList = new ArrayList<>();
		// 리스트를 지웠으므로 새 리스트를 생성해줌.

		// jackson objectmapper 객체 생성
		String cPListJson = objectMapper.writeValueAsString(cPList);
		session.setAttribute("cPListJson", cPListJson);
		// 세션에 리스트를 업데이트

		return ResponseEntity.ok(cPList);
	}

	@GetMapping("/api/album")
	@ResponseBody
	@Transactional
	public ResponseEntity<List<Song>> getAlbum(@RequestParam(value = "albumId") Integer albumId) {
		log.debug("albumId = {}", albumId);
		Album album = albumServ.readById(albumId);
		List<Song> list = album.getSongs();
		log.debug("list = {}", list);
		return ResponseEntity.ok(list);
	}

}
