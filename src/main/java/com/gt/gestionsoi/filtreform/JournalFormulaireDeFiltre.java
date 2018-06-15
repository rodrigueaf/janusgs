package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gt.gestionsoi.entity.Journal;
import com.gt.gestionsoi.repository.spec.JournalSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Journal
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class JournalFormulaireDeFiltre extends BaseFilterForm<Journal> {

    private Journal journal;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Journal> getCriteres() {
        return JournalSpec.allCriteres(this.journal);
    }
}
