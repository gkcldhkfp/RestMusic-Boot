package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GroupLike;
import com.itwill.rest.domain.GroupLikeId;

public interface GroupLikeRepository extends JpaRepository<GroupLike, GroupLikeId> {

}
