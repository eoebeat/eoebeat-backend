package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.Music;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 19/01/23 5:58 AM
 */
public interface MusicRepository extends JpaRepository<Music, String> {

  @Override
  List<Music> findAll();

  @Modifying
  @Transactional
  @Query("UPDATE Music m SET m.hitCount = m.hitCount + :incCount WHERE m.id = :id")
  void incrementHitCount(@Param("id") String id, @Param("incCount") int incCount);

}
