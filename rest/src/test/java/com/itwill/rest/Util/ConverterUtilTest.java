package com.itwill.rest.Util;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongConverter;
import com.itwill.rest.repository.SongConverterRepository;
import com.itwill.rest.repository.SongRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ConverterUtilTest {
	
	@Autowired
    private SongRepository songRepo;
	
	@Autowired
    private SongConverterRepository songConverterRepo;
	
//	@Test
	@Transactional
	public void testEnglishToKoreanSearch() {
	    log.info("testEnglishToKoreanSearch 시작");

	    // 시작 시간 기록
	    long startTime = System.currentTimeMillis();

	    // 1. 영어 검색어 설정
	    String englishSearchTerm = "mirror";
	    String expectedKoreanTitle = "거울";

	    // 2. 영어 검색어로 검색 시작 시간 기록
	    long englishSearchStartTime = System.currentTimeMillis();

	    // 3. 영어 검색어로 검색 수행
	    List<Song> englishSearchResults = songRepo.searchSongs(englishSearchTerm);

	    // 4. 영어 검색 결과 로그 및 검증
	    log.info("English search term: '{}', Result size: {}", englishSearchTerm, englishSearchResults.size());
	    boolean foundExpectedTitle = false;
	    for (Song song : englishSearchResults) {
	        log.info("- {}", song.getTitle());
	        if (song.getTitle().equalsIgnoreCase(expectedKoreanTitle)) {
	            foundExpectedTitle = true;
	        }
	    }
	    assert foundExpectedTitle : "Expected Korean title '" + expectedKoreanTitle + "' not found for English term '" + englishSearchTerm + "'";

	    // 5. 영어 검색 실행 시간 기록 및 출력
	    long englishSearchEndTime = System.currentTimeMillis();
	    long englishSearchDuration = englishSearchEndTime - englishSearchStartTime;
	    log.info("영어 검색어 '{}' 실행 시간: {}ms", englishSearchTerm, englishSearchDuration);

	    // 전체 테스트 종료 시간 기록 및 출력
	    long endTime = System.currentTimeMillis();
	    long duration = endTime - startTime;
	    log.info("testEnglishToKoreanSearch 종료");
	    log.info("전체 테스트 실행 시간: {}ms", duration);
	}
	
//	@Test
	@Transactional
	public void testKoreanToEnglishSearch() {
	    log.info("testKoreanToEnglishSearch 시작");

	    // 시작 시간 기록
	    long startTime = System.currentTimeMillis();

	    // 1. 한글 검색어 설정
	    String koreanSearchTerm = "어텐션";
	    String expectedEnglishTitle = "Attention";

	    // 2. 한글 검색어로 검색 시작 시간 기록
	    long koreanSearchStartTime = System.currentTimeMillis();

	    // 3. 한글 검색어로 검색 수행
	    List<Song> koreanSearchResults = songRepo.searchSongs(koreanSearchTerm);

	    // 4. 한글 검색 결과 로그 및 검증
	    log.info("Korean search term: '{}', Result size: {}", koreanSearchTerm, koreanSearchResults.size());
	    boolean foundExpectedTitle = false;
	    for (Song song : koreanSearchResults) {
	        log.info("- {}", song.getTitle());
	        if (song.getTitle().equalsIgnoreCase(expectedEnglishTitle)) {
	            foundExpectedTitle = true;
	        }
	    }
	    assert foundExpectedTitle : "Expected English title '" + expectedEnglishTitle + "' not found for Korean term '" + koreanSearchTerm + "'";

	    // 5. 한글 검색 실행 시간 기록 및 출력
	    long koreanSearchEndTime = System.currentTimeMillis();
	    long koreanSearchDuration = koreanSearchEndTime - koreanSearchStartTime;
	    log.info("한글 검색어 '{}' 실행 시간: {}ms", koreanSearchTerm, koreanSearchDuration);

	    // 전체 테스트 종료 시간 기록 및 출력
	    long endTime = System.currentTimeMillis();
	    long duration = endTime - startTime;
	    log.info("testKoreanToEnglishSearch 종료");
	    log.info("전체 테스트 실행 시간: {}ms", duration);
	}
	
	@Test
    @Transactional
    public void testPhoneticVariationSearch() {
        log.info("testPhoneticVariationSearch 시작");

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();

        // 1. 발음 변형 검색어 설정
        String phoneticSearchTerm = "rjdnf"; // 'rjdnf'는 '거울'의 발음 변형
        String expectedKoreanTitle = "거울";

        // 2. 발음 변형 검색어로 검색 시작 시간 기록
        long phoneticSearchStartTime = System.currentTimeMillis();

        // 3. 발음 변형 검색어로 검색 수행
        List<Song> phoneticSearchResults = songRepo.searchSongs(phoneticSearchTerm);

        // 4. 발음 변형 검색 결과 로그 및 검증
        log.info("Phonetic search term: '{}', Result size: {}", phoneticSearchTerm, phoneticSearchResults.size());
        boolean foundExpectedTitle = false;
        for (Song song : phoneticSearchResults) {
            log.info("- {}", song.getTitle());
            if (song.getTitle().equalsIgnoreCase(expectedKoreanTitle)) {
                foundExpectedTitle = true;
            }
        }
        assert foundExpectedTitle : "Expected Korean title '" + expectedKoreanTitle + "' not found for phonetic term '" + phoneticSearchTerm + "'";

        // 5. 발음 변형 검색 실행 시간 기록 및 출력
        long phoneticSearchEndTime = System.currentTimeMillis();
        long phoneticSearchDuration = phoneticSearchEndTime - phoneticSearchStartTime;
        log.info("발음 변형 검색어 '{}' 실행 시간: {}ms", phoneticSearchTerm, phoneticSearchDuration);

        // 전체 테스트 종료 시간 기록 및 출력
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("testPhoneticVariationSearch 종료");
        log.info("전체 테스트 실행 시간: {}ms", duration);
    }
	
}
