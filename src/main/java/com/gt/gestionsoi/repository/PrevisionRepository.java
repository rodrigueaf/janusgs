package com.gt.gestionsoi.repository;

import com.gt.gestionsoi.entity.Prevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
