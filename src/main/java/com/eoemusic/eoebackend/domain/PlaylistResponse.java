package com.eoemusic.eoebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 1:36 AM
 */
@Data
@AllArgsConstructor
public class PlaylistResponse {

  private Long id;
  private String playlistName;
}
