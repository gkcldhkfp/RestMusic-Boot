package com.itwill.rest.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.rest.domain.PlayList;
import com.itwill.rest.domain.PlayListSongId;
import com.itwill.rest.dto.playlist.PlayListCreateDto;
import com.itwill.rest.dto.playlist.PlayListFirstAlbumImgDto;
import com.itwill.rest.service.PlayListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlayListController {
	
	private final PlayListService playListSvc;
	
	@GetMapping("/playlists/playlist")
	public void playlist() {
		log.info("playlist()");
	}
	
	@GetMapping("/getPlayList/{id}") // 현재 작동 
	@ResponseBody
	public ResponseEntity<List<PlayListFirstAlbumImgDto>> getPlayList(@PathVariable Integer id) {
		log.info("getPlayList(id={})", id);
		
		List<PlayListFirstAlbumImgDto> result = playListSvc.getPlayListByUserId(id);
		
		return ResponseEntity.ok(result);
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
	public ResponseEntity<Integer> deleteByListId(@PathVariable Integer pListId) {
		log.info("deleteById(pListId={})", pListId);
		
		playListSvc.deleteByListId(pListId);
		
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
		log.debug("addSongToPlayList({})",id);
		
		int result = playListSvc.songAddToPlayList(id);
		
		return ResponseEntity.ok(result);
	}
	
	

}