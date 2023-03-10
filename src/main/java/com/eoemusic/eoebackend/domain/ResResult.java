package com.eoemusic.eoebackend.domain;

import lombok.Data;
import org.apache.http.Header;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 10/03/23 2:54 AM
 */
@Data
public class ResResult {
  private int status;
  private String msg;
  private Header[] reqParams;

  public ResResult(String msg, int status, Header[] reqParams) {
    this.msg = msg;
    this.status = status;
    this.reqParams=reqParams;
  }
  public ResResult(String msg, int status) {
    this.msg = msg;
    this.status = status;
  }
  public ResResult() {
  }

  @Override
  public String toString() {
    return "{\"status\":" + status + ",\"msg\":\"" + msg + "\"}";
  }
}
