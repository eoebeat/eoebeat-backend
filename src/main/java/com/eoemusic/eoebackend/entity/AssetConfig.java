package com.eoemusic.eoebackend.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

/**
 * @Description
 * @Author Xiaoyu Wu
 * @Email: wu.xiaoyu@northeastern.edu
 * @Date 2023-02-27 15:36:36
 */

@Entity
@Getter
@Table(name = "asset_config")
public class AssetConfig implements Serializable {

  private static final long serialVersionUID = 7089057748722117571L;

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "path")
  private String path;

}
