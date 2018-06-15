package com.gt.gestionsoi.repository.spec;

import com.gt.base.repository.spec.BaseSpecifications;
import com.gt.gestionsoi.entity.Categorie;
import com.gt.gestionsoi.entity.PrincipeValeur;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * Specification de l'entité Categorie
 *
 * @author <a href="mailto:claude-rodrigue.affodogandji@ace3i.com?">RODRIGUE
 * AFFODOGANDJI</a>
 * @version 1.0
 * @since 23/10/2017
 */
public class PrincipeValeurSpec extends BaseSpecifications {

    private static Specifications spec;

    private PrincipeValeurSpec() {
        // DO
    }

    /**
     * Filtrer les données
     *
     * @param principeValeur
     * @return
     */
    @SuppressWarnings({"unchecked", "null"})
    public static Specification<PrincipeValeur> allCriteres(PrincipeValeur principeValeur) {

        setSpec(Specifications.where((root, query, cb) -> cb.equal(cb.literal(1), 1)));

        PrincipeValeurSpec.filtrerParLibelle(getSpec(), principeValeur.getLibelle());

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
        PrincipeValeurSpec.spec = spec;
    }
}
