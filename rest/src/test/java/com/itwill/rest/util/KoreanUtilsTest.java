package com.itwill.rest.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Album;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.util.KoreanUtils;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class KoreanUtilsTest {

	@Autowired
	private AlbumRepository albumRepo;

	// @Test
	public void ChosungTest() {
		log.info("ChosungTest");
		String test = "안녕하세요Abc*";
		String chosungTest = KoreanUtils.getInitialSound(test);
		System.out.println(chosungTest);
	}

	// @Test
	@Transactional
	public void chosungAlbumTest() {

		// 시작 시간 기록
		long startTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 시작");

		String test = "ㅊㅊㅎㄱ";
		List<Album> list = albumRepo.findByAlbumNameInitialSound(test);

		// 결과 출력
		System.out.println(list);

		// 종료 시간 기록
		long endTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 종료");

		// 걸린 시간 계산 및 출력
		long duration = endTime - startTime;
		log.info("테스트 실행 시간: " + duration + "ms");
	}
}
