package com.itwill.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Integer>, PlayListQuerydsl {

}
