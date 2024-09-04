package com.itwill.rest.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itwill.rest.dto.AlbumSearchResultDto;
import com.itwill.rest.dto.ArtistSearchResultDto;
import com.itwill.rest.dto.SearchResultDto;
import com.itwill.rest.dto.SongDetailsDto;
import com.itwill.rest.dto.SongSearchResultDto;

public interface SongQuerydsl {

	SongDetailsDto searchDetailsById(int id);
	
	SearchResultDto searchAll(String keyword, String sortType, Pageable pageable);
	
	Page<ArtistSearchResultDto> searchArtists(String keyword, String sortType, Pageable pageable);
	
	Page<AlbumSearchResultDto> searchAlbums(String keyword, String sortType, Pageable pageable);
	
	List<Object[]> findSongsByKeyword(String keyword, int limit, int offset, String sort);
}
