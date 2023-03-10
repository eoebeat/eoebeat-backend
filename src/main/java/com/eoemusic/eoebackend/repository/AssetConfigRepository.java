package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.AssetConfig;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 27/02/23 3:37 PM
 */

public interface AssetConfigRepository extends JpaRepository<AssetConfig, Integer> {

  @Query("SELECT path FROM AssetConfig WHERE name IN :names")
  List<String> findPathsByNames(@Param("names") List<String> names);
  
}
