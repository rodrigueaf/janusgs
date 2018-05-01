package com.gt.gestionsoi.repository.spec;

import com.gt.gestionsoi.entity.Prevision;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Prevision
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class PrevisionSpec extends BaseSpecifications {

    private static Specifications spec;

    private PrevisionSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param prevision
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<Prevision> allCriteres(Prevision prevision) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));
        filtrerParLaDescription(getSpec(), prevision.getDescription());

        return spec;
    }

    private static void filtrerParLaDescription(Specifications spec, String description) {
        if (description != null && !description.isEmpty()) {
            setSpec(spec.and(containsLike("description", description)));
        }
    }

    public static Specifications getSpec() {
        return spec;
    }

    public static void setSpec(Specifications spec) {
        PrevisionSpec.spec = spec;
    }
}
