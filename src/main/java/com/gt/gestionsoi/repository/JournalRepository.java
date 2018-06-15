package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Le repository de l'entité Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface JournalRepository extends BaseEntityRepository<Journal, Integer> {

    Page<Journal> findAllByOrderByIdentifiantDesc(Pageable p);

    @Query("select c from Journal c where c.id in :idSet")
    List<Journal> recupererLaListeVersionnee(@Param("idSet") Integer[] ints);
}
