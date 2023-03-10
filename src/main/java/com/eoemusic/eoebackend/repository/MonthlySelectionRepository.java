package com.eoemusic.eoebackend.repository;

import com.eoemusic.eoebackend.entity.MonthlySelection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 08/03/23 8:04 AM
 */
public interface MonthlySelectionRepository extends JpaRepository<MonthlySelection, Integer> {

  Optional<MonthlySelection> findFirstByOrderByIdAsc();

  List<MonthlySelection> findAllByOrderBySearchLabelDesc();

  @Query("select searchLabel from MonthlySelection")
  List<String> findAllSearchLabel();
}
