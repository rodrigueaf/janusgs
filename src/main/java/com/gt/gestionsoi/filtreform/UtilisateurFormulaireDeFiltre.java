package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.filterform.BaseFilterForm;
import com.gt.gestionsoi.entity.Utilisateur;
import com.gt.gestionsoi.repository.spec.UtilisateurSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Utilisateur
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class UtilisateurFormulaireDeFiltre extends BaseFilterForm<Utilisateur> {

    private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Utilisateur> getCriteres() {
        return UtilisateurSpec.allCriteres(this.utilisateur);
    }
}
