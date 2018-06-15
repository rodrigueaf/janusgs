package com.gt.gestionsoi.repository.spec;

import com.gt.base.repository.spec.BaseSpecifications;
import com.gt.gestionsoi.entity.Processus;
import com.gt.gestionsoi.entity.Projet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Projet
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class ProjetSpec extends BaseSpecifications {

    private static Specifications spec;

    private ProjetSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param projet
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<Projet> allCriteres(Projet projet) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));

        ProjetSpec.filtrerParLibelle(getSpec(), projet.getLibelle());

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
        ProjetSpec.spec = spec;
    }
}
