package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gt.gestionsoi.entity.Prevision;
import com.gt.gestionsoi.repository.spec.PrevisionSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class PrevisionFormulaireDeFiltre extends BaseFilterForm<Prevision> {

    private Prevision prevision;

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Prevision> getCriteres() {
        return PrevisionSpec.allCriteres(this.prevision);
    }
}
