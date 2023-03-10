package com.eoemusic.eoebackend.config;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 10/02/23 3:45 PM
 */

import com.eoemusic.eoebackend.repository.MusicRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HitCounter {

  @Autowired
  private MusicRepository musicRepository;
  private static ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();

  public static void addHit(String musicId) {
    cache.compute(musicId, (k, v) -> v == null ? 1 : v + 1);
  }

  private void syncHitcount() {
    for (Map.Entry<String, Integer> entry : cache.entrySet()) {
      String musicId = entry.getKey();
      int hitCount = entry.getValue();
      log.info("sync hit count, id: {}, with {}", musicId, hitCount);
      musicRepository.incrementHitCount(musicId, hitCount);
      cache.put(musicId, 0);
    }
  }

  public void start() {
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    executor.scheduleAtFixedRate(() -> syncHitcount(), 30, 30, TimeUnit.MINUTES);
  }
}
