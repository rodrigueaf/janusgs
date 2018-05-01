package com.gt.gestionsoi.service;

import com.gt.gestionsoi.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface Service de l'entité Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public interface IJournalService extends IBaseEntityService<Journal, Integer> {

    Page<Journal> findAllByOrderByIdentifiantDesc(Pageable p);
}
