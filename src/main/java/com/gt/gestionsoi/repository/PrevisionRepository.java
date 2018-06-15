package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Prevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Le repository de l'entité Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface PrevisionRepository extends BaseEntityRepository<Prevision, Integer> {
    Page<Prevision> findAllByOrderByIdentifiantDesc(Pageable p);

    @Query("select c from Prevision c where c.id in :idSet")
    List<Prevision> recupererLaListeVersionnee(@Param("idSet") Integer[] ints);
}
