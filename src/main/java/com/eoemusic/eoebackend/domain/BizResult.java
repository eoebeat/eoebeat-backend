package com.eoemusic.eoebackend.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 24/02/23 3:48 PM
 */
@Data
public class BizResult<T> {

  private String bizCode;
  private String bizDesc;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Map<String, T> response;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<T> responseList;

  public BizResult(String bizCode, String bizDesc) {
    this.bizCode = bizCode;
    this.bizDesc = bizDesc;
  }

  public BizResult(String bizCode, String bizDesc, T response) {
    this.bizCode = bizCode;
    this.bizDesc = bizDesc;
  }

  public BizResult(String bizCode, String bizDesc, List<T> responseList) {
    this.bizCode = bizCode;
    this.bizDesc = bizDesc;
    this.responseList = responseList;
  }

}
