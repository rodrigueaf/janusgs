package com.gt.gestionsoi.service;

import com.gt.gestionsoi.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    List<Journal> recupererLaListeVersionnee(Integer[] ints);

    /**
     * Les ligne de journal à importer doivent respecter le format
     * Date;Heure_debut;Heure_fin;Description|
     * où ";" est le séparateur de colonne et "|" est le séparateur de ligne.
     * Chaque ligne correspond à une ligne du journal
     *
     * @param journal
     */
    void importer(String[] journal);
}
