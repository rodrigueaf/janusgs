package com.gt.gestionsoi.repository.spec;

import com.gt.base.repository.spec.BaseSpecifications;
import com.gt.gestionsoi.entity.Objectif;
import com.gt.gestionsoi.entity.Processus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Processus
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class ProcessusSpec extends BaseSpecifications {

    private static Specifications spec;

    private ProcessusSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param processus
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<Processus> allCriteres(Processus processus) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));

        ProcessusSpec.filtrerParLibelle(getSpec(), processus.getLibelle());

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
        ProcessusSpec.spec = spec;
    }
}
