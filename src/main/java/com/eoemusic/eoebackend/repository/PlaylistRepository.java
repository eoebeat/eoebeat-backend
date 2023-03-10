package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.Playlist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 1:41 AM
 */
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

  List<Playlist> findByIdIn(List<Long> playlistIds);
}