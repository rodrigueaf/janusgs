package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.filterform.BaseFilterForm;
import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.repository.spec.ProcessusSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Objectif
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class ProcessusFormulaireDeFiltre extends BaseFilterForm<Processus> {

    private Processus processus;

    public Processus getProcessus() {
        return processus;
    }

    public void setProcessus(Processus processus) {
        this.processus = processus;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Processus> getCriteres() {
        return ProcessusSpec.allCriteres(this.processus);
    }
}
