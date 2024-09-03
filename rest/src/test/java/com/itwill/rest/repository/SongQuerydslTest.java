package com.itwill.rest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.itwill.rest.dto.SearchResultDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SongQuerydslTest {

	@Autowired
	private SongRepository songRepo;
	
	@Autowired
	private AlbumRepository albumRepo;
	
	// @Test
//	@Transactional
	public void querydlstest() {
		// 시작 시간 기록
		long startTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 시작");
//		SongDetailsDto dto = songRepo.searchDetailsById(1);
		Pageable pageable = PageRequest.of(0, 5);
		SearchResultDto dto = songRepo.searchAll("+dum*", "accuracy", pageable);
		log.info("{}", dto);
		log.info("{}", dto.getAlbum());
		log.info("{}", dto.getArtist());
		log.info("{}", dto.getSong());



		// 종료 시간 기록
		long endTime = System.currentTimeMillis();
		log.info("chosungAlbumTest 종료");

		// 걸린 시간 계산 및 출력
		long duration = endTime - startTime;
		log.info("테스트 실행 시간: " + duration + "ms");
		
		
		
//		List<Song> dummy = new ArrayList<Song>();
//		
//		for(int i = 0; i < 1000; i++) {
//			Album dum = Album.builder().albumName("dummy"+i).albumType("dummy").build();
//			albumRepo.save(dum);
//			
//			for(int x = 0; x < 10; x++) {
//				dummy.add(Song.builder().album(dum).title(i+"앨범 song"+x).songPath(".").lyrics("...").build());
//			}
//		}
//		songRepo.saveAll(dummy);
	}
	
	
	
	
	
}
