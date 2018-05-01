package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gt.gestionsoi.entity.Projet;
import com.gt.gestionsoi.repository.spec.ProjetSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class ProjetFormulaireDeFiltre extends BaseFilterForm<Projet> {

    private Projet projet;

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet processus) {
        this.projet = processus;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Projet> getCriteres() {
        return ProjetSpec.allCriteres(this.projet);
    }
}
