package com.eoemusic.eoebackend.service;

import com.eoemusic.eoebackend.domain.NameUrlPair;
import com.eoemusic.eoebackend.domain.PlaylistResponse;
import com.eoemusic.eoebackend.domain.QueryRequest;
import com.eoemusic.eoebackend.domain.QueryResult;
import com.eoemusic.eoebackend.entity.MonthlySelection;
import java.util.List;
import java.util.Map;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 1:30 AM
 */
public interface MusicService {

  Map<String, List<PlaylistResponse>> getPlaylistsByUserId(Long userId);

  QueryResult search(QueryRequest query) throws Exception;


  List<MonthlySelection> getMonthlySelection();

  NameUrlPair[] getPathByNameArr(String[] nameArr, String region);
}
