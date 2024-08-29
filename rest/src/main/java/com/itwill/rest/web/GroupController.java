package com.itwill.rest.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.dto.GroupAlbumDto;
import com.itwill.rest.dto.GroupInfoDto;
import com.itwill.rest.dto.GroupSongDto;
import com.itwill.rest.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/group")
public class GroupController {
	
	private final GroupService groupSvc;
	
	@GetMapping("/songs")
	public void songs(@RequestParam(name = "groupId") Integer groupId, Model model) {
		log.info("songs(groupId={})", groupId);
		
		GroupInfoDto group = groupSvc.getGroupInfoByGroupId(groupId);
		
		List<GroupSongDto> list = groupSvc.readSongs(groupId);
		
		model.addAttribute("group", group);
		model.addAttribute("songs", list);
	}
	
	@GetMapping("/albums")
	public void albums(@RequestParam(name = "groupId") Integer groupId, Model model) {
		log.info("albums(groupId={})", groupId);
		
		GroupInfoDto group = groupSvc.getGroupInfoByGroupId(groupId);
		
		List<GroupAlbumDto> list = groupSvc.readAlbums(groupId);
		
		model.addAttribute("group", group);
		model.addAttribute("albums", list);
	}

}
