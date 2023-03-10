package com.eoemusic.eoebackend.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 3:00 AM
 */
@Data
public class QueryRequest {

  private PageInfo pageable = new PageInfo();
  private List<QueryCondition> condition = new ArrayList<>();
  
  public Map<String, String> getConditionMap() {
    Map<String, String> conditionMap = new HashMap<>();
    condition.forEach(c -> conditionMap.put(c.getName(),c.getValue()));
    return conditionMap;
  }

  public void limit(int pageNumber, int pageSize) {
    pageable.setPage(pageNumber);
    pageable.setSize(pageSize);
  }

  public void addCondition(String name, String value) {
    condition.add(new QueryCondition(name, value));
  }

}
