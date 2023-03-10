package com.eoemusic.eoebackend.domain;

import lombok.Data;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 2:58 AM
 */
@Data
public class PageInfo {
  private int page = -1;   // page = -1 不分页， 页号从 0 开始
  private int size = 20;
  private int total = -1;  // Total = -1  自动统计，否则不统计行数
}
