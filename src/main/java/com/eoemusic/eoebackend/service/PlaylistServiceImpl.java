package com.eoemusic.eoebackend.service;

import com.eoemusic.eoebackend.domain.BizResult;
import com.eoemusic.eoebackend.entity.Playlist;
import com.eoemusic.eoebackend.repository.PlaylistRepository;
import com.eoemusic.eoebackend.repository.UserRepository;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 24/02/23 3:55 PM
 */
@Service("PlaylistService")
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired
  private PlaylistRepository playlistRepository;

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public BizResult initialPlaylist(Long userId) {
    try {
      Playlist playlist = new Playlist();
      playlist.setPlaylistName("已收藏的歌曲");
      Long playlistId = playlistRepository.save(playlist).getId();
      userRepository.updatePlaylistIds(userId, playlistId);
      HashMap<String, Long> map = new HashMap<>();
      map.put("playlistId", playlistId);
      return new BizResult("1", "操作成功", map);
    } catch (Exception e) {
      log.error("initial playlist for user {} error, {}", userId, e);
      return new BizResult("2", "系统内部错误");
    }

  }

}
