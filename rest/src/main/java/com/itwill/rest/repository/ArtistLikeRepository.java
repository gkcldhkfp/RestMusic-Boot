package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.ArtistLike;
import com.itwill.rest.domain.ArtistLikeId;

public interface ArtistLikeRepository extends JpaRepository<ArtistLike, ArtistLikeId> {

}
