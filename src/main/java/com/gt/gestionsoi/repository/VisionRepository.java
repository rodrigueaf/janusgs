package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Vision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Le repository de l'entité Vision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface VisionRepository extends BaseEntityRepository<Vision, Integer> {
    Optional<Vision> findByLibelle(String libelle);

    @Query("select c from Vision c where c.id in :idSet")
    List<Vision> recupererLaListeVersionnee(@Param("idSet") Integer[] ints);
}
