package com.eoemusic.eoebackend.service;

import com.eoemusic.eoebackend.config.AppConfig;
import com.eoemusic.eoebackend.dao.MusicDao;
import com.eoemusic.eoebackend.domain.NameUrlPair;
import com.eoemusic.eoebackend.domain.PlaylistResponse;
import com.eoemusic.eoebackend.domain.QueryRequest;
import com.eoemusic.eoebackend.domain.QueryResult;
import com.eoemusic.eoebackend.entity.MonthlySelection;
import com.eoemusic.eoebackend.entity.Playlist;
import com.eoemusic.eoebackend.entity.User;
import com.eoemusic.eoebackend.repository.AssetConfigRepository;
import com.eoemusic.eoebackend.repository.MonthlySelectionRepository;
import com.eoemusic.eoebackend.repository.PlaylistRepository;
import com.eoemusic.eoebackend.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 1:31 AM
 */
@Service("MusicService")
@Slf4j
public class MusicServiceImpl implements MusicService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlaylistRepository playlistRepository;

  @Autowired
  private AssetConfigRepository assetConfigRepository;

  @Autowired
  private MusicDao musicDao;

  @Autowired
  private AppConfig appConfig;

  @Autowired
  private Environment env;

  @Autowired
  private MonthlySelectionRepository monthlySelectionRepository;

  public Map<String, List<PlaylistResponse>> getPlaylistsByUserId(Long userId) {
    Map<String, List<PlaylistResponse>> map = new HashMap<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      map.put("playerLists", new ArrayList<>());
      return map;
    }
    User user = optionalUser.get();
    String[] playlistIdStr = user.getPlaylistIds().split(",");
    List<Long> playlistIds = Arrays.stream(playlistIdStr).map(Long::parseLong)
        .collect(Collectors.toList());
    List<Playlist> playlistList = playlistRepository.findByIdIn(playlistIds);
    List<PlaylistResponse> playlistResponses = playlistList.stream()
        .map(playlist -> new PlaylistResponse(playlist.getId(), playlist.getPlaylistName()))
        .collect(Collectors.toList());
    map.put("playerLists", playlistResponses);
    return map;
  }

  public QueryResult search(QueryRequest query) throws Exception {
    return musicDao.queryMusic(query);
  }

  public List<NameUrlPair> getPathByNameArr(String[] nameArr, String region) {
    NameUrlPair[] pairArr = new NameUrlPair[nameArr.length];
    List<NameUrlPair> pathArr = assetConfigRepository
        .findNameUrlPairsByNames(Arrays.asList(nameArr));
    if (nameArr == null || nameArr.length != pairArr.length) {
      throw new IllegalArgumentException("name passed has problem");
    }
    String alistUrlPrefix = new StringBuilder()
        .append(appConfig.getIpPort())
        .append("/d")
        .append(env.getProperty("alist.region." + region))
        .toString();
    // need to align with order of nameArr
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < nameArr.length; i++) {
      map.put(nameArr[i], i);
      pathArr.get(i).setUrl(alistUrlPrefix + pathArr.get(i).getUrl());
    }
    Collections.sort(pathArr, (n1, n2) -> {
      int i1 = map.get(n1.getName());
      int i2 = map.get(n2.getName());
      return Integer.compare(i1, i2);
    });
    return pathArr;
  }

  public List<MonthlySelection> getMonthlySelection(String region) {
    String alistUrlPrefix = new StringBuilder()
        .append(appConfig.getIpPort())
        .append("/d")
        .append(env.getProperty("alist.region." + region))
        .toString();
    List<MonthlySelection> monthlySelectionList = monthlySelectionRepository
        .findAllByOrderBySearchLabelDesc();
    monthlySelectionList.forEach(monthlySelection -> {
      monthlySelection.setCoverPath(alistUrlPrefix + monthlySelection.getCoverPath());
    });
    return monthlySelectionList;
  }


}
