package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 20/01/23 1:09 AM
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long id);

  @Modifying
  @Query("update User set playlistIds = concat(playlistIds, ',', :playlistId) where id = :userId")
  void updatePlaylistIds(@Param("userId") Long userId, @Param("playlistId") Long playlistId);

}
