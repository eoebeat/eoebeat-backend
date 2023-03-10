package com.eoemusic.eoebackend.enums;

import lombok.Getter;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 19/01/23 7:35 AM
 */
@Getter
public enum SyncCSVEnum {
  ID(0), UPDATE_TIME(1), SONG_NAME(2),
  SONG_NAME_ALIAS(3), SINGER(4), SONG_DATE(5), VERSION_REMARK(6), AUDIO_MEDIA_TYPE(7), DURATION(
      8), SONG_LANGUAGE(9), SONG_STATUS(
      10);
  private int columnNum;

  SyncCSVEnum(int columnNum) {
    this.columnNum = columnNum;
  }

}
