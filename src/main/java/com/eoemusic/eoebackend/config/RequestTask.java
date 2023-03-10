package com.eoemusic.eoebackend.config;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 10/03/23 4:56 AM
 */

@Slf4j
public class RequestTask implements Runnable {

  int sleepTime;
  String requestUrl;
  AtomicInteger counter;
  

  public RequestTask(String requestUrl, int sleepTime, AtomicInteger counter) {
    this.requestUrl = requestUrl;
    this.sleepTime = sleepTime;
    this.counter = counter;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(sleepTime);
//        ResResult resResult = HttpUtil.doGet(uri);
//        System.out.println("Request sent: " + resResult.getMsg());
      URL url = new URL(requestUrl);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.disconnect();
      log.info(Thread.currentThread().getName() + " has been sent...");
      counter.incrementAndGet();
      System.out.println(counter.get());
    } catch (Exception e) {
      log.error("Failed to send request: " + Thread.currentThread().getName() + " " + e.getMessage());
    }
  }

}
