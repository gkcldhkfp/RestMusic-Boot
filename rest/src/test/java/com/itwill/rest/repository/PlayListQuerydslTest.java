package com.itwill.rest.repository;

import com.itwill.rest.dto.PlayListFirstAlbumImgDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.rest.dto.PlayListFirstAlbumImgDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PlayListQuerydslTest {

    @Autowired
    private PlayListQuerydslImpl playListQuerydsl;

//    @Test
    public void testSelectByUserId() {
        Integer userId = 1; // 실제로 존재하는 userId로 테스트합니다.
        
        List<PlayListFirstAlbumImgDto> playlists = playListQuerydsl.selectByUserId(userId);
        
        assertNotNull(playlists);
//        assertFalse(playlists.isEmpty());

        for (PlayListFirstAlbumImgDto dto : playlists) {
            System.out.println("PlayList ID: " + dto.getPlistId() +
                               ", PlayList Name: " + dto.getPlistName() +
                               ", Album Image: " + dto.getAlbumImage());
        }
    }
}
