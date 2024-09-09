package com.itwill.rest.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="`GROUPS`")
@Getter @NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
@Builder
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private Integer id;

	private String groupName;
	
	private String groupImage;

	private String groupDescription;

	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<GroupMember> groupMembers;
	
	@OneToMany(mappedBy = "group")
	@Builder.Default
	@ToString.Exclude
	@JsonManagedReference // 순환참조 문제를 해결하기 위한 애너테이션
	private List<GroupLike> groupLikes = new ArrayList<>();
	
}