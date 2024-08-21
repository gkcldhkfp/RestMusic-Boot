package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.itwill.rest.domain.QArtist;
import com.itwill.rest.dto.ArtistAlbumDto;

@Repository
public class ArtistQuerydslImpl extends QuerydslRepositorySupport implements ArtistQuerydsl {
	
	public ArtistQuerydslImpl() {
		super(QArtist.class);
	}
	
	@Override
	public List<ArtistAlbumDto> selectAlbumsByArtistId(Integer artistId) {
		return null;
	}

}
