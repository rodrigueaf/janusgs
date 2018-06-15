package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.filterform.BaseFilterForm;
import com.gt.gestionsoi.entity.PrincipeValeur;
import com.gt.gestionsoi.repository.spec.PrincipeValeurSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Profil
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class PrincipeValeurFormulaireDeFiltre extends BaseFilterForm<PrincipeValeur> {

    private PrincipeValeur principeValeur;

    public PrincipeValeur getPrincipeValeur() {
        return principeValeur;
    }

    public void setPrincipeValeur(PrincipeValeur principeValeur) {
        this.principeValeur = principeValeur;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<PrincipeValeur> getCriteres() {
        return PrincipeValeurSpec.allCriteres(this.principeValeur);
    }
}
