package com.eoemusic.eoebackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 11/03/23 5:27 AM
 */
@AllArgsConstructor
public class NameUrlPair {

  @JsonProperty("name")
  String name;
  @JsonProperty("url")
  String url;


}
