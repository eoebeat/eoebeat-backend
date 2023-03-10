package com.eoemusic.eoebackend.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * @Description
 * @Author Xiaoyu Wu
 * @Email: wu.xiaoyu@northeastern.edu
 * @Date 2023-01-18 10:28:07
 */

@Entity
@Data
@Table(name = "music")
@AllArgsConstructor
@NoArgsConstructor
public class Music implements Serializable {

  private static final long serialVersionUID = 5034303725588418323L;

  @Id
  @Column(name = "id")
  private String id;

  /**
   * 更新日期(timestamp)
   */
  @Column(name = "update_time")
  private Long updateTime;


  @Column(name = "song_name")
  private String songName;

  @Column(name = "song_name_alias")
  private String songNameAlias;

  /**
   * 演唱者(空格分隔)
   */
  @Column(name = "singer")
  private String singer;

  /**
   * 歌曲时间(YYYY.MM.DD)
   */
  @Column(name = "song_date")
  private String songDate;

  /**
   * 版本备注
   */
  @Column(name = "version_remark")
  private String versionRemark;

  @Column(name = "audio_media_type")
  private String audioMediaType;

  @Column(name = "cover_media_type")
  private String coverMediaType;

  /**
   * 音频时长(s)
   */
  @Column(name = "duration")
  private Integer duration;

  @Column(name = "song_language")
  private String songLanguage;

  /**
   * 0不完整1完整
   */
  @Column(name = "song_status")
  private String songStatus;

  /**
   * 不含前后缀及歌曲名
   */
  @Column(name = "Alist_audio_path")
  private String alistAudioPath;

  /**
   * 同上
   */
  @Column(name = "Alist_cover_path")
  private String alistCoverPath;

  /**
   * 经过转义
   */
  @Column(name = "partial_url")
  private String partialUrl;

  @Column(name = "hit_count")
  private Integer hitCount;

  /**
   * 歌曲入库时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "insert_time")
  private Date insertTime;

  @PrePersist
  void touchInsertTime() throws InterruptedException {
    DateTime now = DateTime.now();
    Thread.sleep(1);
    setInsertTime(now.toDate());
  }

}
