package com.gt.gestionsoi.repository;

import com.gt.base.repository.BaseEntityRepository;
import com.gt.gestionsoi.entity.Projet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Le repository de l'entité Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/06/2017
 */
public interface ProjetRepository extends BaseEntityRepository<Projet, Integer> {
    Optional<Projet> findByLibelle(String libelle);

    @Query("select c from Projet c where c.id in :idSet")
    List<Projet> recupererLaListeVersionnee(@Param("idSet") Integer[] ints);
}
