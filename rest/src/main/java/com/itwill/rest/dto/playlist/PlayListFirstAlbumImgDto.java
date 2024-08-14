package com.itwill.rest.dto.playlist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
public class PlayListFirstAlbumImgDto {
	private Integer plistId;
	private String plistName;
	private String albumImage;

}
