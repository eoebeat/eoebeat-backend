package com.eoemusic.eoebackend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @Description
 * @Author Xiaoyu Wu
 * @Email: wu.xiaoyu@northeastern.edu
 * @Date 2023-01-20 01:10:06
 */

@Entity
@Data
@Table(name = "playlist")
public class Playlist implements Serializable {

  private static final long serialVersionUID = 2228111275599692905L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name = "playlist_name")
  private String playlistName;

  @Column(name = "music_ids")
  private String musicIds;

}
