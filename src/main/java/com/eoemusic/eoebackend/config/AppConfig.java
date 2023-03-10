package com.eoemusic.eoebackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 16/02/23 11:57 AM
 */
@Configuration
@Getter
public class AppConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Value("${alist.ipPort}")
  private String ipPort;

  private String coverMediaType = "png";
  
  @Value("${alist.monthlySelectionCoverPath}")
  private String monthlySelectionCoverPath;


}