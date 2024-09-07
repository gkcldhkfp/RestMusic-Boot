package com.itwill.rest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ALBUMS_FIRST_CON")
@Getter @NoArgsConstructor 
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @Builder @ToString
public class AlbumFirstCon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ALBUM_FIRST_CON_ID")
//    private Long id;
    private Integer id;

    @OneToOne
    @JoinColumn(name = "album_id")
		@ToString.Exclude
    private Album album;

    private String albumFirstConName;
	
}