package com.itwill.rest.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentDto {
    private String type;
    private Integer id;
    private String name;
    private LocalDate releaseDate; // or use java.util.Date
    private Integer likeCount;

  
}


