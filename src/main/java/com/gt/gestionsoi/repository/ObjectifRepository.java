package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Objectif;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Le repository de l'entité Objectif
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface ObjectifRepository extends BaseEntityRepository<Objectif, Integer> {
    Optional<Objectif> findByLibelle(String libelle);

    @Query("select c from Objectif c where c.id in :idSet")
    List<Objectif> recupererLaListeVersionnee(@Param("idSet") Integer[] ints);
}
