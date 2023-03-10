package com.eoemusic.eoebackend;

import com.eoemusic.eoebackend.config.HitCounter;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Slf4j
@Configuration
public class EoebackendApplication extends SpringBootServletInitializer {

//  @Value(value = "${init.createDatabase}")
//  private boolean creatDatabase;
//  @Value(value = "${init.databaseIpPort}")
//  private String databaseIpPort;
//  @Value(value = "${spring.datasource.username}")
//  private String userName;
//  @Value(value = "${spring.datasource.password}")
//  private String password;
//  @Value(value = "${init.databaseName}")
//  private String databaseName;

  public static void main(String[] args) {
    SpringApplication.run(EoebackendApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(EoebackendApplication.class);
  }

  @Autowired
  private HitCounter hitCounter;

  @PostConstruct
  public void startHitCounter() {
    hitCounter.start();
  }
  
  @PostConstruct
  void started() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
  }

//
//  @PostConstruct
//  public void init() {
//    if (creatDatabase) {
//      try (Connection conn = DriverManager
//          .getConnection("jdbc:mysql://" + databaseIpPort + "/mysql", userName,
//              password)) {
//        log.info("init database........");
//        conn.prepareStatement("create database if not exists " + databaseName + ";").execute();
//        log.info("database " + databaseName + " has been successfully initialized........");
//      } catch (Exception e) {
//        log.error("init database failed!", e);
//      }
//    }
//  }
}
  

