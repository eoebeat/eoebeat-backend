package com.eoemusic.eoebackend.controller;

import com.eoemusic.eoebackend.config.RequestTask;
import com.eoemusic.eoebackend.entity.Music;
import com.eoemusic.eoebackend.entity.User;
import com.eoemusic.eoebackend.repository.MusicRepository;
import com.eoemusic.eoebackend.repository.UserRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 14/01/23 4:02 AM
 */
@RestController
@Slf4j
@RequestMapping("/eoebackend")
public class TestController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/test")
  public String test() {
    log.info("hit test");
    return "Successfully get backend!";
  }

  @PostMapping("/insertUser")
  public void insertUser() {
    User user = new User();
    user.setUsername("test");
    user.setPhoneCountry("086");
    user.setPhoneNumber("111");
    user.setPlaylistIds("1");
    userRepository.save(user);
  }

  @Autowired
  private MusicRepository musicRepository;

  @GetMapping("/testSharePointRequestNum")
  public void testSharePointRequestNum() throws InterruptedException {
    String urlPrefix = "http://106.14.12.237:5244/d/cntest/audio";
    List<Music> musics = musicRepository.findAll();
    int requestsPerMinute = 600;
    int maxDelayTimeInMills = 60 * 1000;
    Random random = new Random();
    long startTime = System.currentTimeMillis();
    Thread[] threads = new Thread[requestsPerMinute];
    AtomicInteger counter = new AtomicInteger(0);
    for (int i = 0; i < requestsPerMinute; i++) {
      String requestUrl =
          urlPrefix + musics.get(random.nextInt(musics.size())).getPartialUrl() + ".m4a";
      threads[i] = new Thread(
          new RequestTask(requestUrl, random.nextInt(maxDelayTimeInMills), counter));
      threads[i].start();
    }
    for (int i = 0; i < requestsPerMinute; i++) {
      threads[i].join();
    }
    long elapsedTime = System.currentTimeMillis() - startTime;
    log.info("Sent " + requestsPerMinute + " requests in " + elapsedTime + " ms");

  }


}
