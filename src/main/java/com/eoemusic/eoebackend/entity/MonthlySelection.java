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
 * @Date 2023-03-08 08:41:47
 */

@Entity
@Data
@Table(name = "monthly_selection")
public class MonthlySelection implements Serializable {

  private static final long serialVersionUID = 809530093043065133L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  /**
   * 2023年3月
   */
  @Column(name = "name")
  private String name;

  @Column(name = "cover_path")
  private String coverPath;

  @Column(name = "description")
  private String description;

  /**
   * 2023.03
   */
  @Column(name = "search_label")
  private String searchLabel;

}
