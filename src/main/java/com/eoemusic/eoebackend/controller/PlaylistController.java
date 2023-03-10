package com.eoemusic.eoebackend.controller;

import com.eoemusic.eoebackend.domain.BizResult;
import com.eoemusic.eoebackend.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 24/02/23 3:19 PM
 */
@RestController
@Slf4j
@RequestMapping("/eoebackend/playlist")
public class PlaylistController {

  @Autowired
  private PlaylistService playlistService;

  //todo initial '已收藏的歌曲' playlist
  @GetMapping("/initial/{userId}")
  public BizResult initialPlaylist(@PathVariable("userId") Long userId) {
    return playlistService.initialPlaylist(userId);
  }
  
  //todo create a playlist
  @PostMapping("/createPlaylist")
  public BizResult createPlaylist(Long userId, String playlistName){
    
    return null;
  }
  //todo delete a playlist
  //todo add a music into a playlist
}
