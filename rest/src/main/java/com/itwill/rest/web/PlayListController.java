package com.itwill.rest.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSongId;
import com.itwill.rest.domain.User;
import com.itwill.rest.dto.PlayListCreateDto;
import com.itwill.rest.dto.PlayListFirstAlbumImgDto;
import com.itwill.rest.dto.PlayListNameUpdateDto;
import com.itwill.rest.dto.PlayListSongInfoDto;
import com.itwill.rest.service.PlayListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayListController {

	private final PlayListService playListSvc;

	@GetMapping("/playlists/playlist")
	public void playlist(@RequestParam(name = "plistId") Integer pListId, Model model, Authentication authentication) {
		log.info("playlist(plistId={})", pListId);

		Integer loginUserId = null;
		if (authentication != null && authentication.isAuthenticated()) {
			User user = (User) authentication.getPrincipal();
			loginUserId = user.getId();
		}
		model.addAttribute("loginUserId", loginUserId);

		PlayList playList = playListSvc.getPlayListInfoByListId(pListId);

		model.addAttribute("playList", playList);
	}

	@GetMapping("/getPlayList/{id}")
	@ResponseBody
	public ResponseEntity<List<PlayListFirstAlbumImgDto>> getPlayList(@PathVariable(name="id") Integer id) {
		log.info("getPlayList(id={})", id);

		List<PlayListFirstAlbumImgDto> result = playListSvc.getPlayListByUserId(id);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getPlayListSong/{id}")
	@ResponseBody
	public ResponseEntity<List<PlayListSongInfoDto>> getPlayListSong(@PathVariable(name="id") Integer id) {
		log.info("getPlayListSong(id={})", id);

		List<PlayListSongInfoDto> playListSongInfo = playListSvc.getSongByPlayListId(id);
		return ResponseEntity.ok(playListSongInfo);
	}

	@PostMapping("/addPlayList")
	@ResponseBody
	public ResponseEntity<PlayList> addPlayList(@RequestBody PlayListCreateDto dto) {
		log.info("addPlayList()");

		PlayList entity = playListSvc.create(dto);
		log.info("save 결과: {}", entity);

		return ResponseEntity.ok(entity);
	}

	@DeleteMapping("/deletePlayList/{pListId}")
	@ResponseBody
	public ResponseEntity<Integer> deleteByListId(@PathVariable(name="id") Integer pListId) {
		log.info("deleteById(pListId={})", pListId);

		playListSvc.deleteByListId(pListId);

		return ResponseEntity.ok(pListId);
	}

	@DeleteMapping("/deletePlayListSong/{pListId}/{songId}/{createdTime}")
	@ResponseBody
	public ResponseEntity<Integer> deleteListSongBySongId(
			@PathVariable(name="pListId") Integer pListId, @PathVariable(name="songId") Integer songId, @PathVariable(name="createdTime") String createdTime) {
		log.info("deleteListSongBySongId(pListId={})", pListId);

		LocalDateTime localDateTime = LocalDateTime.parse(createdTime, DateTimeFormatter.ISO_DATE_TIME);

		playListSvc.deleteListSongBySongId(pListId, songId, localDateTime);

		return ResponseEntity.ok(pListId);
	}

	@PostMapping("/checkSongInPlayList")
	@ResponseBody
	public ResponseEntity<Boolean> checkSongInPlayList(@RequestBody PlayListSongId id) {
		log.debug("checkSongInPlayList");

		Boolean result = playListSvc.checkSongInPlayList(id);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/addSongToPlayList")
	@ResponseBody
	public ResponseEntity<Integer> addSongToPlayList(@RequestBody PlayListSongId id) {
		log.debug("addSongToPlayList({})", id);

		int result = playListSvc.songAddToPlayList(id);

		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/updatePlayListName/{pListId}")
	@ResponseBody
	public ResponseEntity<Integer> updatePlayListName(@PathVariable(name = "pListId") Integer pListId, @RequestBody PlayListNameUpdateDto dto) {
		log.info("updatePlayListName(id={}, dto={})", pListId, dto);
		
		dto.setPListId(pListId);
		
    	playListSvc.updatePlayListNameByListId(dto);
    	
    	return ResponseEntity.ok(pListId);
	}
	
}
