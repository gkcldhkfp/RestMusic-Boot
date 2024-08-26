package com.itwill.rest.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.rest.domain.Group;
import com.itwill.rest.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/group")
public class GroupController {
	
	private GroupService groupSvc;
	
	@GetMapping("/songs")
	public void songs(@RequestParam(name = "groupId") Integer groupId, Model model) {
		log.info("songs(groupId={})", groupId);
		
		Group group = groupSvc.findById(groupId);
		
		model.addAttribute("group", group);
	}
	
	@GetMapping("/albums")
	public void albums(@RequestParam(name = "groupId") Integer groupId, Model model) {
		log.info("albums(groupId={})", groupId);
		
		Group group = groupSvc.findById(groupId);
		
		model.addAttribute("group", group);
	}

}
