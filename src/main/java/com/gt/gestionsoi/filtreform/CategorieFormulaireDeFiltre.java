package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gt.base.filterform.BaseFilterForm;
import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.repository.spec.CategorieSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Profil
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class CategorieFormulaireDeFiltre extends BaseFilterForm<Categorie> {

    private Categorie categorie;
    private Boolean parent;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Categorie> getCriteres() {
        return CategorieSpec.allCriteres(this);
    }
}
