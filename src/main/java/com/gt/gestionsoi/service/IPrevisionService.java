package com.gt.gestionsoi.service;

import com.gt.gestionsoi.entity.Prevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface Service de l'entité Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public interface IPrevisionService extends IBaseEntityService<Prevision, Integer> {

    Page<Prevision> findAllByOrderByIdentifiantDesc(Pageable p);
}
