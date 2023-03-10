package com.eoemusic.eoebackend.service;

import com.eoemusic.eoebackend.domain.BizResult;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 24/02/23 3:55 PM
 */
public interface PlaylistService {

  BizResult initialPlaylist(Long userId);
}
