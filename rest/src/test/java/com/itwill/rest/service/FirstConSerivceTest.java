package com.itwill.rest.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.rest.dto.ChoSeongSearchDto;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FirstConSerivceTest {

	@Autowired
	private FirstConService firstConServ;

//	@Test
	public void albumFirstConInsert() {
		// 기존 앨범 테이블과 신규 초성 테이블을 동기화하는 초기세팅 테스트 메서드
		// 기존 앨범 테이블의 albumName 컬럼을 초성화 한 후에 초성 테이블에 save하는 서비스 계층 메서드 호출
		firstConServ.synchAlbumFirstCon();
		// synchAlbumFirstCon.forEach(s -> System.out.println(s));
	}

//	 @Test
	public void songFirstConInsert() {
		// 기존 앨범 테이블과 신규 초성 테이블을 동기화하는 초기세팅 테스트 메서드
		// 기존 앨범 테이블의 songName 컬럼을 초성화 한 후에 초성 테이블에 save하는 서비스 계층 메서드 호출
		firstConServ.synchSongFirstCon();
		// synchSongFirstCon.forEach(s -> System.out.println(s));
	}

//	@Test
	public void artistFirstConInsert() {
		// 기존 앨범 테이블과 신규 초성 테이블을 동기화하는 초기세팅 테스트 메서드
		// 기존 앨범 테이블의 artistName 컬럼을 초성화 한 후에 초성 테이블에 save하는 서비스 계층 메서드 호출
		firstConServ.synchArtistFirstCon();
		// synchArtistFirstCon.forEach(s -> System.out.println(s));
	}

//	@Test
	public void groupFirstConInsert() {
		// 기존 앨범 테이블과 신규 초성 테이블을 동기화하는 초기세팅 테스트 메서드
		// 기존 앨범 테이블의 groupName 컬럼을 초성화 한 후에 초성 테이블에 save하는 서비스 계층 메서드 호출
		firstConServ.synchGroupFirstCon();
		// synchGroupFirstCon.forEach(s -> System.out.println(s));
	}

//	@Test
	public void searchDtos() {
		List<ChoSeongSearchDto> searchDtos = firstConServ.searchDtos("ㅂㅇㄱ");
		searchDtos.forEach(x -> System.out.println(x));
	}
	
}
