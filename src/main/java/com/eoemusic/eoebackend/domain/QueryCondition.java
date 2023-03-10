package com.eoemusic.eoebackend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 3:00 AM
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class QueryCondition {
  private String name;
  private String value;
}
