package com.eoemusic.eoebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 7:12 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponse {

  private String id;
  private String songDate;
  private String singer;
  private String songName;
  private String songNameAlias;
  private String versionRemark;
  private String audioUrl;
  private String coverUrl;
  private int duration;
  private String songLanguage;
  private String songStatus;
  private int hitCount;
  private String insertTime;
}
