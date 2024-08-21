package com.itwill.rest.repository;

import java.util.List;

import com.itwill.rest.dto.ArtistAlbumDto;

public interface ArtistQuerydsl {
	
	List<ArtistAlbumDto> selectAlbumsByArtistId(Integer artistId);

}
