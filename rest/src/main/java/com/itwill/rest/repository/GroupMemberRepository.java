package com.itwill.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.rest.domain.GroupMember;
import com.itwill.rest.domain.GroupMemberId;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
	List<GroupMember> findByGroupId(Integer groupId);
	
}
