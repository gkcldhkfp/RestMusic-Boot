package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.domain.GroupMemberId;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
	// 특정 아티스트의 그룹 멤버 목록을 반환하는 메서드 추가
    List<GroupMember> findByArtist_Id(Integer artistId);
    
    List<GroupMember> findByGroupId(Integer groupId);
}
