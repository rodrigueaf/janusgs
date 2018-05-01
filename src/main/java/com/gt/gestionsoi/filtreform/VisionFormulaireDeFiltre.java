package com.gt.gestionsoi.filtreform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.gt.gestionsoi.entity.Vision;
import com.gt.gestionsoi.repository.spec.VisionSpec;
import org.springframework.data.jpa.domain.Specification;

/**
 * Formulaire de filtre sur les dnnées de Vision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class VisionFormulaireDeFiltre extends BaseFilterForm<Vision> {

    private Vision vision;

    public Vision getVision() {
        return vision;
    }

    public void setVision(Vision vision) {
        this.vision = vision;
    }

    /**
     * Recuperer les donnees du filtre
     *
     * @return
     */
    @JsonIgnore
    @Override
    public Specification<Vision> getCriteres() {
        return VisionSpec.allCriteres(this.vision);
    }
}
