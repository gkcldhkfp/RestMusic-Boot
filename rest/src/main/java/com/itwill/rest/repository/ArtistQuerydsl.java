package com.itwill.rest.repository;

import java.util.List;

import com.itwill.rest.dto.ArtistAlbumDto;
import com.itwill.rest.dto.ArtistSongDto;

public interface ArtistQuerydsl {
	
	List<ArtistSongDto> selectSongsByArtistId(Integer artistId);
	
	List<ArtistAlbumDto> selectAlbumsByArtistId(Integer artistId);

}
