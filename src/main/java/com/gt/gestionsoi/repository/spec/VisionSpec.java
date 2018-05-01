package com.gt.gestionsoi.repository.spec;

import com.gt.gestionsoi.entity.Vision;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Vision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class VisionSpec extends BaseSpecifications {

    private static Specifications spec;

    private VisionSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param categorie
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<Vision> allCriteres(Vision categorie) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));

        VisionSpec.filtrerParLibelle(getSpec(), categorie.getLibelle());

        return spec;
    }

    private static void filtrerParLibelle(Specifications spec, String libelle) {
        if (libelle != null && !libelle.isEmpty()) {
            setSpec(spec.and(containsLike("libelle", libelle)));
        }
    }

    public static Specifications getSpec() {
        return spec;
    }

    public static void setSpec(Specifications spec) {
        VisionSpec.spec = spec;
    }
}
