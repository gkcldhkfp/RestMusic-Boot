package com.itwill.rest.dto;

import java.util.List;

import com.itwill.rest.domain.Artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class GroupInfoDto {
    private Integer groupId;
    private String groupName;
    private String groupDescription;
    private String groupImage;
    private List<Artist> artists;

}
