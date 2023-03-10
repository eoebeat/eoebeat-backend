package com.eoemusic.eoebackend.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 09/02/23 9:13 PM
 */
@Getter
public enum SearchOrderByEnum {
  HIT_COUNT(" order by hit_count desc, song_date desc, insert_time desc"),
  SONG_DATE(" order by song_date desc, insert_time desc"),
  SONG_DATE_ASC(" order by song_date, insert_time desc"),
  INSERT_TIME(" order by song_date, insert_time desc");
  private String SQL;

  SearchOrderByEnum(String SQL) {
    this.SQL = SQL;
  }

}
