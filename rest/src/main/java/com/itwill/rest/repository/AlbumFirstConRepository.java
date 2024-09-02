package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.rest.domain.AlbumFirstCon;
import com.itwill.rest.dto.ChoSeongSearchDto;

public interface AlbumFirstConRepository extends JpaRepository<AlbumFirstCon, Long> {

}
