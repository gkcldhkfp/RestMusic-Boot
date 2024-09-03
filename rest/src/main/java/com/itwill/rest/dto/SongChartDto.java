package com.itwill.rest.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongChartDto {
	private Integer songId; // 음악 번호
    private Integer albumId; // 앨범 번호
    private String albumImage; // 앨범 표지
    private String title; // 노래 제목
    private List<Integer> artistIds; // 아티스트 번호 리스트
    private List<String> artistNames; // 아티스트 이름 리스트
    private String albumName; // 앨범 이름
    private Integer likes; // 좋아요 개수
    private String songPath; // 음악 파일 경로
    private String videoLink; // 뮤비 링크
//	private Integer genreId; // 장르 번호
//	private String genreName; // 장르 이름
    private LocalDate albumReleaseDate; // 앨범 발매일
    
    private List<Integer> genreIds; // 장르 번호
	private List<String> genreNames; // 장르 이름
    private List<Integer> groupIds; // 그룹 번호
    private List<String> groupNames; // 그룹 이름
}
