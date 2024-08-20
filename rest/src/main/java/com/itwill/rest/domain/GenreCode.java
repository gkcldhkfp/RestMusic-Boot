package com.itwill.rest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="GENRE_CODE")
@Getter
@NoArgsConstructor @AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder @ToString
@EqualsAndHashCode
public class GenreCode {
	@Builder.Default
	private Integer codeId = 2;
	
	@Id
	private int genreId;

	private String genreName;
}
