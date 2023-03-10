package com.eoemusic.eoebackend.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 2:58 AM
 */
@Data
@NoArgsConstructor
public class QueryResult<T> {
  private PageInfo pageable;
  private List<T> items = new ArrayList<>();

  public QueryResult(List<T> item, PageInfo pageable) {
    if (item != null) {
      this.items.addAll(item);
    }
    this.pageable = pageable;
  }
}
