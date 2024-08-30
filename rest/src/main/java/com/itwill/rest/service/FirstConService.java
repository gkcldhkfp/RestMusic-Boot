package com.itwill.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.rest.domain.Album;
import com.itwill.rest.domain.AlbumFirstCon;
import com.itwill.rest.domain.Song;
import com.itwill.rest.domain.SongFirstCon;
import com.itwill.rest.repository.AlbumFirstConRepository;
import com.itwill.rest.repository.AlbumRepository;
import com.itwill.rest.repository.SongFirstConRepository;
import com.itwill.rest.repository.SongRepository;
import com.itwill.rest.util.KoreanUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirstConService {

	private final AlbumRepository albumRepo;
	private final AlbumFirstConRepository albumFirstConRepo;

	private final SongRepository songRepo;
	private final SongFirstConRepository songFirstConRepo;


	/**
	 * 앨범 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<AlbumFirstCon> synchAlbumFirstCon() {
		List<Album> albums = albumRepo.findAll();
		// 현재 모든 앨범을 불러옴.
		List<AlbumFirstCon> albumFirstCons = new ArrayList<>();
		for (Album album : albums) {
			AlbumFirstCon albumFirstConName = AlbumFirstCon.builder()
					.album(album)
					.albumFirstConName(KoreanUtils.getInitialSound(album.getAlbumName()))
					.build();
			// 초성 앨범 객체를 생성
			albumFirstCons.add(albumFirstConName);
			// 리스트에 추가
		}
		List<AlbumFirstCon> savedAlbumFirstCon = albumFirstConRepo.saveAll(albumFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedAlbumFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}

	/**
	 * 음원 데이터를 초성테이블에 변환 후 삽입하는 서비스 메서드
	 */
	public List<SongFirstCon> synchSongFirstCon() {
		List<Song> songs = songRepo.findAll();
		// 현재 모든 앨범을 불러옴.
		List<SongFirstCon> songFirstCons = new ArrayList<>();
		for (Song song : songs) {
			SongFirstCon songFirstConName = SongFirstCon.builder()
					.song(song)
					.songFirstConName(KoreanUtils.getInitialSound(song.getTitle()))
					.build();
			// 초성 앨범 객체를 생성
			songFirstCons.add(songFirstConName);
			// 리스트에 추가
		}
		List<SongFirstCon> savedSongFirstCon = songFirstConRepo.saveAll(songFirstCons);
		// 리스트를 데이터베이스에 삽입.
		return savedSongFirstCon;
		// 결과 확인 용 리스트를 리턴.
	}
}